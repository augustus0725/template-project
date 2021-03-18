package com.sabo.checker;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.security.UserGroupInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.util.Properties;

@SpringBootApplication
public class KerberosChecker implements CommandLineRunner {

    private static Properties properties() throws Exception {
        Properties properties = new Properties();

        properties.load(new FileInputStream("config.properties"));
        return properties;
    }

    private static Logger LOG = LoggerFactory
            .getLogger(KerberosChecker.class);

    public static void main(String[] args) {
        LOG.info("STARTING THE APPLICATION");
        SpringApplication.run(KerberosChecker.class, args);
        LOG.info("APPLICATION FINISHED");
    }

    @Override
    public void run(String... args) throws Exception {
        Configuration config = HBaseConfiguration.create();
        System.setProperty("sun.security.krb5.debug", "true");
        Properties properties = properties();

        String base = properties.getProperty("base");
        String principal = properties.getProperty("principal");

        System.out.println("base : " + base);
        System.out.println("principal : " + principal);

        // 减少重试次数
        config.set("java.security.krb5.conf", base + "/krb5.conf");
        config.setInt("hbase.client.retries.number",2);
        config.addResource(new Path(base, "hbase-site.xml"));
        config.addResource(new Path(base, "core-site.xml"));
        UserGroupInformation.setConfiguration(config);
        UserGroupInformation.loginUserFromKeytab(principal,
                base + "/" + "my.keytab");

        System.out.println("=========================================");
        System.out.println(UserGroupInformation.getCurrentUser());
        System.out.println("=========================================");
    }

}
