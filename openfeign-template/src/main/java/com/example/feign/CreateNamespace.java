package com.example.feign;

import feign.HeaderMap;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.Map;

public interface CreateNamespace {
    @RequestLine("POST /dolphinscheduler/projects/create")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    CreateNamespaceResp create(@HeaderMap Map<String, String> headers, @Param("projectName") String namespace,
                @Param("description") String description);

    class CreateNamespaceResp {
        private Integer code;
        private String msg;
        private Integer data; /* 返回了 project id */

        @Override
        public String toString() {
            return "CreateNamespaceResp{" +
                    "code=" + code +
                    ", msg='" + msg + '\'' +
                    ", data=" + data +
                    '}';
        }
    }
}
