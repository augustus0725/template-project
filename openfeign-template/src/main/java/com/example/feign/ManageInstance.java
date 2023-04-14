package com.example.feign;

import feign.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Map;

public interface ManageInstance {
    // id => xxx
    @RequestLine("POST /dolphinscheduler/projects/{namespace}/schedule/online")
    ManageInstanceResp start(@HeaderMap Map<String, String> headers, @Param("namespace") String namespace,
                             @QueryMap Map<String, Object> queryMap);

    @RequestLine("POST /dolphinscheduler/projects/{namespace}/schedule/offline")
    ManageInstanceResp stop(@HeaderMap Map<String, String> headers, @Param("namespace") String namespace,
                             @QueryMap Map<String, Object> queryMap);

    @Data
    class ManageInstanceResp {
        private Integer code;
        private String msg;
        private Integer data;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @SuperBuilder
    class DolphinScheduler {
        private String scheduleTime;
        private String failureStrategy;
        private String warningType;
        private String processInstancePriority;
        private String warningGroupId;
        private String receivers;
        private String execType;
        private String startNodeList;
        private String taskDependType;
        private String runMode;
        private String receiversCc;
        private String workerGroup;
        private String processDefinitionId;
    }

    @RequestLine("POST /dolphinscheduler/projects/{namespace}/executors/start-process-instance")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    ManageInstanceResp startOnce(@HeaderMap Map<String, String> headers, @Param("namespace") String namespace,
                                 DolphinScheduler scheduler);
}
