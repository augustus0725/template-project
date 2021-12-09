package org.example;

import org.example.proxy.ExecutorProxy;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        new ExecutorProxy("mvvj-app01\\target", "org.example.MvvjApp").execute("sabo");
        new ExecutorProxy("mvvj-app02\\target", "org.example.MvvjApp").execute("sabo");
        System.out.println();
    }
}
