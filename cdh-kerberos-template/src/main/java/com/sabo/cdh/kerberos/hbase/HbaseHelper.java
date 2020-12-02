package com.sabo.cdh.kerberos.hbase;

import com.google.common.collect.Queues;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.TableDescriptor;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.security.UserGroupInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author crazy
 * date: 2020/12/1
 */
public class HbaseHelper implements Closeable {
    private final static int HBASE_POOL_SIZE = 8;
    @SuppressWarnings("UnstableApiUsage")
    private final LinkedBlockingQueue<Connection> pool = Queues.newLinkedBlockingQueue(HBASE_POOL_SIZE);
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Object mLock = new Object();

    private volatile Configuration conf;

    private ScheduledExecutorService scheduledExecutorService;

    public HbaseHelper() {
    }

    private Configuration createConfiguration() {
        Configuration config = HBaseConfiguration.create();
        System.setProperty("java.security.krb5.conf", "hbase-conf/krb5.conf");

        //Add any necessary configuration files (hbase-site.xml, core-site.xml)
        // 减少重试次数
        config.set("java.security.krb5.conf", "hbase-conf/krb5.conf");
        config.setInt("hbase.client.retries.number",2);
        config.addResource(new Path("hbase-conf", "hbase-site.xml"));
        config.addResource(new Path("hbase-conf", "core-site.xml"));

        return config;
    }

    public void init() throws Exception {
        // add empty connections
        for (int i = 0; i < HBASE_POOL_SIZE; i++) {
            try {
                pool.put(EmptyConnection.emptyConnection());
            } catch (InterruptedException e) {
                logger.error("Fail to init null connection pool.", e);
            }
        }
        // do kinit
        conf = createConfiguration();
        UserGroupInformation.setConfiguration(conf);
        UserGroupInformation.loginUserFromKeytab("vagrant@hongwang.com", "hbase-conf/vagrant.keytab");

        // create schedule
        final ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setDaemon(true)
                .setNameFormat("data-service-worker-%d")
                .build();
        scheduledExecutorService = new ScheduledThreadPoolExecutor(2,
                namedThreadFactory);

        // add kerberos ticket re-newer
        scheduledExecutorService.scheduleWithFixedDelay(() -> {
            try {
                UserGroupInformation ugi = UserGroupInformation.getCurrentUser();
                ugi.checkTGTAndReloginFromKeytab();
                System.out.println("renew success .........................");
            } catch (IOException e) {
                logger.error("Fail to renew ticket.", e);
            }
        }, 10, 60, TimeUnit.SECONDS);
    }

    public Connection acquireConnection() throws Exception {
        Connection connection = null;
        synchronized (mLock) {
            connection = pool.take();
            if (connection instanceof EmptyConnection) {
                connection = ConnectionFactory.createConnection(conf);
            }
        }
        if (null == connection || connection instanceof EmptyConnection) {
            throw new Exception("Fail to acquire connection.");
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        try {
            pool.put(connection);
        } catch (InterruptedException e) {
            logger.error("Fail to release connection", e);
        }
    }

    @Override
    public void close() {
        for (Connection connection : pool) {
            IOUtils.closeQuietly(connection);
        }
    }


    // test
    public static void main(String[] args) throws Exception {
        final HbaseHelper hbaseHelper = new HbaseHelper();
        hbaseHelper.init();
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            new Thread(() -> {
                Connection connection = null;
                Admin admin = null;
                try {
                    connection = hbaseHelper.acquireConnection();
                    admin = connection.getAdmin();

                    List<TableDescriptor> tableDescriptors = admin.listTableDescriptorsByNamespace(Bytes.toBytes("default"));
                    for (TableDescriptor table : tableDescriptors) {
                        System.out.println(table.getTableName() + " : " + finalI);
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("Thread : " + finalI + " begin to end.");
                    IOUtils.closeQuietly(admin);
                    if (null != connection) {
                        hbaseHelper.releaseConnection(connection);
                    }
                    System.out.println("Thread : " + finalI + " ended.");
                }
            }, "sabo:" + i).start();
        }


        Thread.sleep(1_000 * 120);
        hbaseHelper.close();
    }
}