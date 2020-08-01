package com.sabo.cdh.kerberos.hive;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author canbin.zhang
 * date: 2019/12/12
 */
public class HiveTemplate {
    private static final String JDBC_DRIVER_NAME = "org.apache.hive.jdbc.HiveDriver";

    private static final String CONNECTION_URL = "jdbc:hive2://" + "192.168.0.110"
            + ':' + 10000 + "/default;"
            + "principal=hive/cdh01.hw.com@HONGWANG.COM";

    public static void main(String[] args) throws Exception{
        // kerberos auth as kinit
        Configuration config = new Configuration();
        System.setProperty("java.security.krb5.conf", "hive-conf/krb5.conf");
        System.setProperty("sun.security.krb5.debug", "true");
        config.addResource(new Path("hive-conf", "core-site.xml"));
        UserGroupInformation.setConfiguration(config);
        UserGroupInformation.loginUserFromKeytab("hongwang@HONGWANG.COM", "hive-conf/hongwang-cdh.keytab");

        // JDBC
        Class.forName(JDBC_DRIVER_NAME);
        Connection con = DriverManager.getConnection(CONNECTION_URL);

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM default.stu");

        System.out.println("\n== Begin Query Results ======================");

        // print the results to the console
        while (rs.next()) {
            // the example query returns one String column
            System.out.println(rs.getString(1));
        }

        System.out.println("== End Query Results =======================\n\n");
    }
}
