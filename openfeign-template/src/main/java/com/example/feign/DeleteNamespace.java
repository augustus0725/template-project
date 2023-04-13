package com.example.feign;

import feign.HeaderMap;
import feign.Headers;
import feign.QueryMap;
import feign.RequestLine;

import java.util.Map;

public interface DeleteNamespace {
    @RequestLine("GET /dolphinscheduler/projects/delete")
    DeleteNamespaceResp delete(@HeaderMap Map<String, String> headers, @QueryMap Map<String, Object> queryMap);

    class DeleteNamespaceResp {
        private Integer code;
        private String msg;
        private Integer data;

        @Override
        public String toString() {
            return "DeleteNamespaceResp{" +
                    "code=" + code +
                    ", msg='" + msg + '\'' +
                    ", data=" + data +
                    '}';
        }
    }

}
