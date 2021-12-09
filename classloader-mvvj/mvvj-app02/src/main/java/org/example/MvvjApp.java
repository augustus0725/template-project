package org.example;

public class MvvjApp implements Executor {
    @Override
    public void execute(String name) {
        System.out.println("App02: Hello " + name);
    }
}
