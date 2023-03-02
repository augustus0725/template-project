package org.example.proxy;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

public class JdbcProxy {
    private final StandardExecutorClassLoader classLoader;
    private final Map<String, Driver> nameDriver = new HashMap<>();

    public JdbcProxy(StandardExecutorClassLoader classLoader) {
        this.classLoader = classLoader;
    }


    public Connection getConnection(String driver, String url, String user, String password) throws Exception {
        Driver d = nameDriver.get(driver);
        if (null == d) {
            d = (Driver) Class.forName(driver, true, this.classLoader).newInstance();
            // Driver d 是由this.classLoader 加载的, 但是DriverManager只认系统classLoader加载的驱动
            // 所以用ShimDriver包了一层, ShimDriver是由系统classLoader加载的
            DriverManager.registerDriver(new ShimDriver(d));
            nameDriver.put(driver, d);
        }
        return DriverManager.getConnection(url, user, password);
    }
}
