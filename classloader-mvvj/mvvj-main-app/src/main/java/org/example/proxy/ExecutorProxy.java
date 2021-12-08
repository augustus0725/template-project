package org.example.proxy;

import org.example.Executor;

import java.io.IOException;
import java.lang.reflect.Method;

public class ExecutorProxy implements Executor {
    private final StandardExecutorClassLoader classLoader;
    private final String clazz;

    public ExecutorProxy(String path, String clazz) throws IOException {
        classLoader = new StandardExecutorClassLoader(path);
        this.clazz = clazz;
    }

    @Override
    public void execute(String name) {
        try {
            Class<?> executorClazz = classLoader.loadClass(clazz);
            Object executorInstance = executorClazz.newInstance();
            Method executeMethod = executorClazz.getMethod("execute", String.class);
            executeMethod.invoke(executorInstance, name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
