package com.sabo;

import org.modeshape.common.collection.Problems;
import org.modeshape.jcr.JcrRepository;
import org.modeshape.jcr.ModeShapeEngine;
import org.modeshape.jcr.RepositoryConfiguration;

import javax.jcr.Node;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) throws Exception {
        // Create and start the engine ...
        ModeShapeEngine engine = new ModeShapeEngine();
        engine.start();

        RepositoryConfiguration config = RepositoryConfiguration.read("my-repository-config.json");

        // Verify the configuration for the repository ...
        Problems problems = config.validate();
        if (((Problems) problems).hasErrors()) {
            System.err.println("Problems with the configuration.");
            System.err.println(problems);
            System.exit(-1);
        }

        JcrRepository repository = engine.deploy(config); // 也可以使用工厂方法产生repository

        problems = repository.getStartupProblems();
        if (problems.hasErrors() || problems.hasWarnings()) {
            System.err.println("Problems deploying the repository.");
            System.err.println(problems);
            System.exit(-1);
        }

        // session

        javax.jcr.Session session = repository.login("default");
        // Get the root node ...
        Node root = session.getRootNode();
        assert root != null;

        System.out.println("Found the root node in the \"" + session.getWorkspace().getName() + "\" workspace");
        session.logout();

        // close engine
        Future<Boolean> future = engine.shutdown();
        if ( future.get() ) {  // blocks until the engine is shutdown
            System.out.println("Shutdown successful");
        }

    }
}
