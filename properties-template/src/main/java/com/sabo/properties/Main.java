package com.sabo.properties;

import java.io.InputStreamReader;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties properties;

        try (InputStreamReader reader = new InputStreamReader(Main.class.getResourceAsStream("/config.properties"),  "utf-8")) {
            properties = new Properties();
            properties.load(reader);
            System.out.println(properties.getProperty("value"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
