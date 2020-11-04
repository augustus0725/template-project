package com.sabo.main;

import com.sabo.HelloWorld;
import com.sabo.HelloWorldService;

/**
 * @author crazy
 * date: 2020/11/5
 */
public class HelloClient {
    public static void main(String[] args) {
        HelloWorld helloWorld = new HelloWorldService().getHelloWorldPort();

        System.out.println(helloWorld.sayHello());
    }
}
