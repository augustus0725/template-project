package org.example;

public class MvvjApp01 implements Executor{
    @Override
    public void execute(String name) {
        System.out.println("App01: Hello " + name);
    }
}
