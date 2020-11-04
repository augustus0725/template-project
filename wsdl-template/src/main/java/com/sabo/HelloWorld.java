package com.sabo;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;

/**
 * @author crazy
 * date: 2020/11/5
 */
@WebService
public class HelloWorld {
    public String sayHello() {
        return "Hello world!";
    }

    public static void main(String[] args) {
        Endpoint.publish("http://127.0.0.1:9999/helloworld", new HelloWorld());
    }
}
