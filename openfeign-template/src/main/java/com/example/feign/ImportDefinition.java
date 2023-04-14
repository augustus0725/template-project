package com.example.feign;

import feign.HeaderMap;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.io.File;
import java.util.Map;

public interface ImportDefinition {
    @RequestLine("POST /dolphinscheduler/projects/import-definition")
    @Headers("Content-Type: multipart/form-data")
    ImportDefinitionResp importDefinition(@HeaderMap Map<String, String> headers, @Param("projectName") String namespace,
                          @Param("file") File definition);

    class ImportDefinitionResp {
        private Integer code;
        private String msg;
        private Integer data;

        @Override
        public String toString() {
            return "ImportDefinitionResp{" +
                    "code=" + code +
                    ", msg='" + msg + '\'' +
                    ", data=" + data +
                    '}';
        }
    }
}
