package com.sabo.spring;

import com.alibaba.druid.util.JdbcUtils;
import com.google.common.base.Strings;
import com.sabo.spring.provider.JdbcProvider;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class SpringBootConsoleApplication
        implements CommandLineRunner {

    private static Logger LOG = LoggerFactory
            .getLogger(SpringBootConsoleApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootConsoleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        LOG.info("EXECUTING : command line runner");
        HelpFormatter formatter = new HelpFormatter();
        CommandLineParser parser = new DefaultParser();
        CommandLine line;
        Options options = new Options();
        Option configOption = Option.builder("c") // -c
                .longOpt("config") // --config
                .argName("config")
                .hasArg(true)
                .desc("指定配置文件路径")
                .build();
        Option sqlOption = Option.builder("s")
                .longOpt("sql")
                .argName("sql")
                .hasArg(true)
                .desc("执行的sql")
                .build();

        Option hlpOption = Option.builder("h")
                .longOpt("help")
                .argName("help")
                .hasArg(false)
                .desc("打印帮助信息")
                .build();


        options.addOption(configOption);
        options.addOption(sqlOption);
        options.addOption(hlpOption);

        line = parser.parse( options, args );

        if (line.hasOption("h")) {
            formatter.printHelp( "run-sql", options );
            return;
        }

        String config = null;
        if (line.hasOption("c")) {
            config = line.getOptionValue("c");
        } else {
            if (Files.exists(Paths.get("/etc/run-sql/config"))) {
                config = "/etc/run-sql/config";
            }
        }
        if (Strings.isNullOrEmpty(config)) {
            LOG.error("配置文件不存在");
            return;
        }
        String sql = line.getOptionValue("s");

        if (Strings.isNullOrEmpty(sql)) {
            LOG.error("sql不能为空");
            return;
        }
        // Get datasource from config
        DataSource dataSource = JdbcProvider.dataSource(config);
        if (sql.charAt(0) == 's' || sql.charAt(0) == 'S') {
            List<Map<String, Object>> records = JdbcUtils.executeQuery(dataSource, sql);
            if (!records.isEmpty()) {
                // print head
                String printHead = "";
                for (Map.Entry<String, Object> entry : records.get(0).entrySet()) {
                    printHead += (entry.getKey() + "\t");
                }
                System.out.println(printHead + "\n");

                // print value
                for (Map<String, Object> record : records) {
                    String printRecord = "";
                    for (Map.Entry<String, Object> entry : record.entrySet()) {
                        printRecord += (entry.getValue() + "\t");
                    }
                    System.out.println(printRecord + "\n");
                }
            }
        } else {
            JdbcUtils.execute(dataSource, sql);
        }
    }
}