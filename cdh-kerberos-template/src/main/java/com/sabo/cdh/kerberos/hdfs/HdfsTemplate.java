package com.sabo.cdh.kerberos.hdfs;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;

/**
 * @author canbin.zhang
 * date: 2019/12/11
 */
public class HdfsTemplate {
    public static void main(String[] args) throws IOException {
        Configuration config = new Configuration();
        System.setProperty("java.security.krb5.conf", "hadoop-conf/krb5.conf");
        System.setProperty("sun.security.krb5.debug", "true");
        config.addResource(new Path("hadoop-conf", "core-site.xml"));
        config.addResource(new Path("hadoop-conf", "hdfs-site.xml"));
        UserGroupInformation.setConfiguration(config);
        UserGroupInformation.loginUserFromKeytab("hongwang@HONGWANG.COM", "hadoop-conf/hongwang-cdh.keytab");

        FileSystem fs = FileSystem.get(config);
        fs.mkdirs(new Path("/user/hongwang/hw-tmp"));
        FileStatus[] fsStatus = fs.listStatus(new Path("/"));
        for (int i = 0; i < fsStatus.length; i++) {
            System.out.println(fsStatus[i].getPath().toString());
        }
    }
}
