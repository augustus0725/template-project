package org.example;

public class Hello implements org.example.api.Hello{
    @Override
    public String say() {
        return "Hi, nice to meet you!";
    }
}
