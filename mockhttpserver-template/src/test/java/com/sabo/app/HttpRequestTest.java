package com.sabo.app;

import com.google.common.base.Charsets;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

import static org.junit.Assert.*;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;

public class HttpRequestTest {
    private static ClientAndServer mockServer;

    @BeforeClass
    public static void startServer() {
        mockServer = startClientAndServer(1080);
    }

    @AfterClass
    public static void stopServer() {
        mockServer.stop();
    }

    @Test
    public void hello() {
        // define api input & output
        new MockServerClient("127.0.0.1", 1080)
                .when(
                        HttpRequest.request()
                        .withMethod("GET")
                        .withPath("/hello")
                )
                .respond(HttpResponse.response()
                        .withBody("world", Charsets.UTF_8)
                );
        // do test
        assertEquals("world", new HttpRequestApp().hello());
    }
}