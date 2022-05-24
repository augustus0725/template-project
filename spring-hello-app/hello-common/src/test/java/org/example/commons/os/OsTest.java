package org.example.commons.os;

import lombok.SneakyThrows;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.*;

public class OsTest {

    @SneakyThrows
    @Test
    public void saveToTmpFile() {
        Path path = null;
        try {
            path = Os.saveToTmpFile("Hello world!");
            assertTrue(Files.exists(path));
        } catch (Exception ignore) {

        } finally {
            if (null != path) {
                Files.deleteIfExists(path);
            }
        }
    }
}