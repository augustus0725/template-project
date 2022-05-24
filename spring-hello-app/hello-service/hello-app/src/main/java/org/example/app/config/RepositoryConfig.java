package org.example.app.config;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Slf4j
public class RepositoryConfig {
    // TODO 需要修改成目标项目的包
    public final static String JPA_PACKAGE_HOME = "org.example";
    public final static String JPA_ENTITY_HOME = JPA_PACKAGE_HOME + ".jpa.entity";
    private static Pattern REPOSITORY_PATTERN;


    static {
        String driverClassName = null;
        String result;
        String DB_TYPE = "pg";
        // 从配置文件里读取jdbcUrl, 然后检测出是什么库, 覆盖 DB_TYPE
        try {
            // 先从 resource 里获取 application.yml
            //noinspection UnstableApiUsage
            result = findJdbcUrl(Resources.asCharSource(
                    Resources.getResource("application.yml"),
                    StandardCharsets.UTF_8).readLines());

            if (!Strings.isNullOrEmpty(result)) {
                driverClassName = result;
            }

            // 然后从 spring.profiles.active 参数里获取 application-<spring.profiles.active>.yml
            String active = System.getenv("spring.profiles.active");
            String activePath = String.format("application-%s.yml", active);

            if (!Strings.isNullOrEmpty(active) && java.nio.file.Files.exists(Paths.get(activePath))) {
                //noinspection UnstableApiUsage
                result = findJdbcUrl(Files.readLines(new File(activePath), StandardCharsets.UTF_8));
                if (!Strings.isNullOrEmpty(result)) {
                    driverClassName = result;
                }
            }

            // 从给的driverClassName猜测数据库的类型
            if (!Strings.isNullOrEmpty(driverClassName)) {
                final Map<String, String> driverDBMapping = Maps.newHashMap();

                driverDBMapping.putAll(
                        // ImmutableMap最多只有5个键值对, 用这个只是写起来简单
                        ImmutableMap.of(
                                "org.postgresql.Driver", "pg",
                                "oracle.jdbc.OracleDriver", "oracle",
                                "oracle.jdbc.driver.OracleDriver", "oracle",
                                "com.mysql.jdbc.Driver", "mysql",
                                "com.microsoft.sqlserver.jdbc.SQLServerDriver", "mssql"
                        )
                );
                driverDBMapping.putAll(
                        ImmutableMap.of(
                                "dm.jdbc.driver.DmDriver", "dm"
                        )
                );
                result = driverDBMapping.get(driverClassName);
                if (!Strings.isNullOrEmpty(driverClassName)) {
                    DB_TYPE = result;
                }
            }
            // build matcher
            REPOSITORY_PATTERN = Pattern.compile(
                    RW.REPOSITORY_PACKAGE.replace(".", "\\.") + "\\.[0-9a-zA-Z_]{1,100}"
                            + "|" +
                    RW.REPOSITORY_PACKAGE.replace(".", "\\.") + "\\." + DB_TYPE + "\\.[0-9a-zA-Z_]{1,100}"
                            + "|" +
                    R.REPOSITORY_PACKAGE.replace(".", "\\.") + "\\.[0-9a-zA-Z_]{1,100}"
                            + "|" +
                    R.REPOSITORY_PACKAGE.replace(".", "\\.") + "\\." + DB_TYPE + "\\.[0-9a-zA-Z_]{1,100}"
            );
        } catch (Exception e) {
            log.error("Fail to read jdbcUrl from configuration.", e);
        }
    }

    private static String findJdbcUrl(List<String> lines) {
        for (String line : lines) {
            String trimmedLine = line.trim();

            if (trimmedLine.startsWith("driverClassName")) {
                String[] parts = trimmedLine.split(" ");

                if (parts.length >= 2) {
                    return parts[parts.length - 1];
                }
            }
        }
        return null;
    }

    @SuppressWarnings("NullableProblems")
    public final static class RW implements TypeFilter {
        public final static String REPOSITORY_PACKAGE = JPA_PACKAGE_HOME + ".jpa.repository.rw";

        @Override
        public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
            log.info("rw:" + metadataReader.getClassMetadata().getClassName() + "==>" +
                    REPOSITORY_PATTERN.matcher(metadataReader.getClassMetadata().getClassName()).matches());
            return REPOSITORY_PATTERN.matcher(metadataReader.getClassMetadata().getClassName()).matches();
        }
    }

    @SuppressWarnings("NullableProblems")
    public final static class R implements TypeFilter {
        public final static String REPOSITORY_PACKAGE = JPA_PACKAGE_HOME + ".jpa.repository.r";

        @Override
        public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
            log.info("r:" + metadataReader.getClassMetadata().getClassName() + "==>"
                    + REPOSITORY_PATTERN.matcher(metadataReader.getClassMetadata().getClassName()).matches());
            return REPOSITORY_PATTERN.matcher(metadataReader.getClassMetadata().getClassName()).matches();
        }
    }
}
