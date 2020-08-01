package com.sabo.invoke;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;

public class RunCmd {
    private void run(CommandLine commandLine) {
        DefaultExecutor executor = new DefaultExecutor();
        PumpStreamHandler streamHandler = new PumpStreamHandler(System.out);
        executor.setStreamHandler(streamHandler);

        try {
            executor.execute(commandLine);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CommandLine commandLine = new CommandLine("vagrant");
        commandLine.addArguments(new String[]{"-v"});
        new RunCmd().run(commandLine);
    }
}
