package com.sabo.app;

import org.apache.http.client.fluent.Request;

/**
 *
 * https://www.baeldung.com/mockserver
 */

public class HttpRequestApp {
    public String hello() {
        final String url = "http://127.0.0.1:1080/hello";
        String content = null;
        try {
            content = Request.Get(url)
                    .connectTimeout(1000)
                    .socketTimeout(1000)
                    .execute()
                    .returnContent()
                    .asString();

        } catch (Exception ignore) {
            // ignore errors
        }
        return content;
    }
}
