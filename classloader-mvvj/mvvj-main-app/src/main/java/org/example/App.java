package org.example;

import org.example.proxy.ExecutorProxy;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        new ExecutorProxy("mvvj-app01\\target", "org.example.MvvjApp01").execute("sabo");
        new ExecutorProxy("mvvj-app02\\target", "org.example.MvvjApp02").execute("sabo");
    }
}
