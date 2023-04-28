package com.example.feign;

import feign.HeaderMap;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Map;

public interface ConfigDefinitionSchedule {
    @Getter
    @Setter
    @NoArgsConstructor
    @SuperBuilder
    class DolphinScheduler {
        private String schedule;
        private String failureStrategy;
        private String warningType;
        private String processInstancePriority;
        private String warningGroupId;
        private String receivers;
        private String receiversCc;
        private String workerGroup;
        private String processDefinitionId;
    }

    @RequestLine("POST /dolphinscheduler/projects/{namespace}/schedule/create")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    ConfigDefinitionScheduleResp sched(@HeaderMap Map<String, String> headers, @Param("namespace") String namespace,
                                       DolphinScheduler scheduler);

    @Data
    class ConfigDefinitionScheduleResp {
        private Integer code;
        private String msg;
        private Integer data;
    }

    @Data
    @SuperBuilder
    class ScheduleDetail {
        private String startTime;
        private String endTime;
        private String crontab;
    }

}

