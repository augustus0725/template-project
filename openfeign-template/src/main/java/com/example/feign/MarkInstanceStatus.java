package com.example.feign;

import feign.HeaderMap;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.Map;

public interface MarkInstanceStatus {
    @RequestLine("POST /dolphinscheduler/projects/{namespace}/process/release")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    MarkInstanceStatusResp mark(@HeaderMap Map<String, String> headers, @Param("namespace") String namespace,
                                @Param("processId") long instanceId, @Param("releaseState") int status);

    class MarkInstanceStatusResp {
        private Integer code;
        private String msg;
        private Integer data;

        @Override
        public String toString() {
            return "MarkInstanceStatusResp{" +
                    "code=" + code +
                    ", msg='" + msg + '\'' +
                    ", data=" + data +
                    '}';
        }
    }
}
