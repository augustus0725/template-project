package org.example.config;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class RepositoryConfigTest {
    @Test
    public void testMatcher() {
        String pattern = RepositoryConfig.RW.REPOSITORY_PACKAGE.replace(".", "\\.") + "\\.[0-9a-zA-Z_]{1,100}"
                + "|" +
                RepositoryConfig.RW.REPOSITORY_PACKAGE.replace(".", "\\.") + "\\." + "pg" + "\\.[0-9a-zA-Z_]{1,100}"
                + "|" +
                RepositoryConfig.R.REPOSITORY_PACKAGE.replace(".", "\\.") + "\\.[0-9a-zA-Z_]{1,100}"
                + "|" +
                RepositoryConfig.R.REPOSITORY_PACKAGE.replace(".", "\\.") + "\\." + "pg" + "\\.[0-9a-zA-Z_]{1,100}";
        Pattern REPOSITORY_PATTERN = Pattern.compile(
                pattern
        );


        assertTrue(REPOSITORY_PATTERN.matcher("org.example.jpa.repository.rw.UserRepositoryTest").matches());
        assertTrue(REPOSITORY_PATTERN.matcher("org.example.jpa.repository.rw.UserRepository").matches());
        assertTrue(REPOSITORY_PATTERN.matcher("org.example.jpa.repository.r.UserRepositoryTest").matches());
        assertTrue(REPOSITORY_PATTERN.matcher("org.example.jpa.repository.r.UserRepository").matches());
        assertTrue(REPOSITORY_PATTERN.matcher("org.example.jpa.repository.r.pg.UserRepository").matches());
        assertFalse(REPOSITORY_PATTERN.matcher("org.example.jpa.repository.r.mysql.UserRepository").matches());
        assertTrue(REPOSITORY_PATTERN.matcher("org.example.jpa.repository.r.UserReadOnlyRepository").matches());

    }

}