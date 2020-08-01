package com.sabo.spring;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootConsoleApplication
        implements CommandLineRunner {

    private static Logger LOG = LoggerFactory
            .getLogger(SpringBootConsoleApplication.class);

    public static void main(String[] args) {
        LOG.info("STARTING THE APPLICATION");
        SpringApplication.run(SpringBootConsoleApplication.class, args);
        LOG.info("APPLICATION FINISHED");
    }

    @Override
    public void run(String... args) throws ParseException {
        LOG.info("EXECUTING : command line runner");
        HelpFormatter formatter = new HelpFormatter();
        CommandLineParser parser = new DefaultParser();
        CommandLine line;
        Options options = new Options();
        Option runOption = Option.builder("r") // -r
                .longOpt("run") // --run
                .argName("TIMES")
                .hasArg(true)
                .required(true)
                .desc("运行命令")
                .build();

        options.addOption(runOption);
        formatter.printHelp( "common-tools", options );

        line = parser.parse( options, args );
        if (line.hasOption("r")) {
            System.out.println("run value : " + line.getOptionValue("r"));
        }
        /**
         * usage: common-tools
         *  -r,--run <TIMES>   运行命令
         * run value : 100
         */
    }
}