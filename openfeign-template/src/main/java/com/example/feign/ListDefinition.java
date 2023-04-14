package com.example.feign;

import feign.HeaderMap;
import feign.Param;
import feign.RequestLine;

import java.util.List;
import java.util.Map;

public interface ListDefinition {
    @RequestLine("GET /dolphinscheduler/projects/{namespace}/process/list")
    ListDefinitionResp listDefinitions(@HeaderMap Map<String, String> headers, @Param("namespace")String namespace);
    class ListDefinitionResp {
        private Integer code;
        private String msg;
        private List<Definition> data;

        @Override
        public String toString() {
            return "ListDefinitionResp{" +
                    "code=" + code +
                    ", msg='" + msg + '\'' +
                    ", data=" + data +
                    '}';
        }
    }

    class Definition {
        private Long id;
        private String name;

        @Override
        public String toString() {
            return "Definition{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
