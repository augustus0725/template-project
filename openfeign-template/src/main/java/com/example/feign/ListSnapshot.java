package com.example.feign;

import feign.HeaderMap;
import feign.Param;
import feign.QueryMap;
import feign.RequestLine;
import lombok.Data;

import java.util.List;
import java.util.Map;

public interface ListSnapshot {
    // "/schedule/list-paging?processDefinitionId="
    //                        + templateId + "&searchVal=&pageNo=1&pageSize=256"
    @RequestLine("GET /dolphinscheduler/projects/{namespace}/schedule/list-paging")
    ListSnapshotResp snapshots(@HeaderMap Map<String, String> headers, @Param("namespace") String namespace,
                               @QueryMap Map<String, Object> queryMap);

    @Data
    public static class ListSnapshotResp {
        private int code;
        private String msg;
        private DataList data;
    }

    @Data
    public static class DataList {
        @Data
        public static class Element {
            private long id;
            private long processDefinitionId;
        }

        private List<Element> totalList;
    }
}
