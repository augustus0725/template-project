package com.example;

import com.example.feign.*;
import com.google.common.collect.ImmutableMap;
import com.google.gson.GsonBuilder;
import feign.Feign;
import feign.form.FormEncoder;
import feign.gson.GsonDecoder;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

public class Main {
    static void funcMyApi() {
        MyApi myApi = Feign.builder()
                .decoder(new GsonDecoder())
                .target(MyApi.class, "https://www.juhe.cn");

        MyApi.Keyword keyword = myApi.keyword();
        System.out.println(keyword);
    }

    static void funcCreateNamespace() {
        CreateNamespace createNamespace = Feign.builder()
                .encoder(new FormEncoder())
                .decoder(new GsonDecoder())
                .target(CreateNamespace.class, "http://192.168.0.59:12345");

        CreateNamespace.CreateNamespaceResp resp = createNamespace.create(Collections.singletonMap(
                "token",
                "b29bc87c05911c7aefde0724231155aa"
        ), "myprj01", "it's nice.");
        System.out.println(resp);
    }

    static void funcDeleteNamespace() {
        DeleteNamespace deleteNamespace = Feign.builder()
                .decoder(new GsonDecoder())
                .target(DeleteNamespace.class, "http://192.168.0.59:12345");

        DeleteNamespace.DeleteNamespaceResp resp = deleteNamespace.delete(Collections.singletonMap(
                "token",
                "b29bc87c05911c7aefde0724231155aa"
        ), ImmutableMap.of(
                "projectId", 12
        ));
        System.out.println(resp);
    }

    static void funcImportDefinition() {
        ImportDefinition importDefinition = Feign.builder()
                .encoder(new FormEncoder())
                .decoder(new GsonDecoder())
                .target(ImportDefinition.class, "http://192.168.0.59:12345");

        ImportDefinition.ImportDefinitionResp resp = importDefinition.importDefinition(Collections.singletonMap(
                "token",
                "b29bc87c05911c7aefde0724231155aa"
        ), "feign-demo", Paths.get("dag01.json").toFile());
        System.out.println(resp);
    }

    static void funcListDefinitions() {
        ListDefinition listDefinition = Feign.builder()
                .decoder(new GsonDecoder())
                .target(ListDefinition.class, "http://192.168.0.59:12345");

        ListDefinition.ListDefinitionResp resp = listDefinition.listDefinitions(Collections.singletonMap(
                "token",
                "b29bc87c05911c7aefde0724231155aa"
        ), "feign-demo");
        System.out.println(resp);
    }

    static void funcMarkDefinition() {
        MarkInstanceStatus markInstanceStatus = Feign.builder()
                .encoder(new FormEncoder())
                .decoder(new GsonDecoder())
                .target(MarkInstanceStatus.class, "http://192.168.0.59:12345");

        MarkInstanceStatus.MarkInstanceStatusResp resp = markInstanceStatus.mark(Collections.singletonMap(
                "token",
                "b29bc87c05911c7aefde0724231155aa"
        ), "feign-demo", 51027, 1);
        System.out.println(resp);
    }

    static void funcConfigDefinitionSchedule() {
        ConfigDefinitionSchedule sched = Feign.builder()
                .encoder(new FormEncoder())
                .decoder(new GsonDecoder())
                .target(ConfigDefinitionSchedule.class, "http://192.168.0.59:12345");

        ConfigDefinitionSchedule.ConfigDefinitionScheduleResp resp = sched.sched(Collections.singletonMap(
                        "token",
                        "b29bc87c05911c7aefde0724231155aa"
                ), "feign-demo",
                ConfigDefinitionSchedule.DolphinScheduler.builder()
                        .schedule(
                                new GsonBuilder().create().toJson(
                                        ConfigDefinitionSchedule.ScheduleDetail.builder()
                                                .startTime(
                                                        LocalDateTime.now()
                                                                .atZone(ZoneId.of("Asia/Shanghai"))
                                                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                                                ).endTime(
                                                        LocalDateTime.now().plusYears(100)
                                                                .atZone(ZoneId.of("Asia/Shanghai"))
                                                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                                                ).crontab("0 0 * * * ? *").build()
                                )
                        )
                        .failureStrategy("END")
                        .warningType("NONE")
                        .processInstancePriority("MEDIUM")
                        .warningGroupId("0")
                        .receivers("")
                        .receiversCc("")
                        .workerGroup("default")
                        .processDefinitionId("51027")
                        .build()
        );
        System.out.println(resp);

    }

    static void listSnapshot() {
        ListSnapshot listSnapshot = Feign.builder()
                .decoder(new GsonDecoder())
                .target(ListSnapshot.class, "http://192.168.0.59:12345");
        ListSnapshot.ListSnapshotResp resp = listSnapshot.snapshots(
                Collections.singletonMap(
                        "token",
                        "b29bc87c05911c7aefde0724231155aa"
                ),
                "feign-demo",
                ImmutableMap.of(
                        "processDefinitionId", "51027",
                        "searchVal", "",
                        "pageNo", "0",
                        "pageSize", "1"
                )
        );
        System.out.println(resp);
    }

    public static void main(String[] args) {
//        funcCreateNamespace();
//        funcDeleteNamespace();
//        funcImportDefinition();
//        funcListDefinitions();
        // => 导入模板看不到实例id, 查找实例号用另外一个请求  不是原子的 有风险 TODO 修改下ds的代码
//        funcMarkDefinition();
        listSnapshot();
    }
}
