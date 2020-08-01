package com.sabo.cdh.kerberos.impala;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;

import java.security.PrivilegedAction;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author canbin.zhang
 * date: 2019/12/12
 */
public class ImpalaTemplate {
    private final static String CONNECTION_URL = "jdbc:impala://cdh03.hw.com:21050/default;AuthMech=1" +
             ";KrbRealm=HONGWANG.COM;KrbHostFQDN=cdh03.hw.com;KrbServiceName=impala;principal=impala";

    public static void main(String[] args) throws Exception{
        System.setProperty("java.security.krb5.conf", "hive-conf/krb5.conf");
        System.setProperty("sun.security.krb5.debug", "true");

        Configuration config = new Configuration();
        config.addResource(new Path("hive-conf", "core-site.xml"));
        UserGroupInformation.setConfiguration(config);
        UserGroupInformation.loginUserFromKeytab("hongwang", "hive-conf/hongwang-cdh.keytab");

        System.out.println(UserGroupInformation.getLoginUser());
        UserGroupInformation loginUser = UserGroupInformation.getLoginUser();

        Connection conn = loginUser.doAs(new PrivilegedAction<Connection>() {
            @Override
            public Connection run() {
                try {
                    Class.forName("com.cloudera.impala.jdbc41.Driver");
                    return DriverManager.getConnection(CONNECTION_URL);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM default.stu ");

        System.out.println("\n== Begin Query Results ======================");

        // print the results to the console
        while (rs.next()) {
            // the example query returns one String column
            System.out.println(rs.getString(1));
        }

        System.out.println("== End Query Results =======================\n\n");
    }

}
