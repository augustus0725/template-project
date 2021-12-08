package org.example.proxy;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

public class StandardExecutorClassLoader extends URLClassLoader {
    public StandardExecutorClassLoader(String path) throws IOException {
        super(new URL[]{}, null);
        loadResource(path);
    }

    private void loadResource(String path) throws IOException {
        tryLoadJarInDir(path);
        tryLoadJarInDir(path + File.separator + "lib");
    }

    private void tryLoadJarInDir(String path) throws IOException {
        File dir = new File(path);

        if (dir.exists() && dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                if (file.isFile() && file.getName().endsWith(".jar")) {
                    this.addURL(file);
                }
            }
        }
    }

    private void addURL(File file) throws IOException {
        super.addURL(file.toURI().toURL());
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        return super.loadClass(name, resolve);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            return super.findClass(name);
        } catch (Exception ignore) {
            return StandardExecutorClassLoader.class.getClassLoader().loadClass(name);
        }
    }


}
