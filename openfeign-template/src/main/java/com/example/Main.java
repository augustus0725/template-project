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

    public static final String TOKEN_ORIG = "b29bc87c05911c7aefde0724231155aa";
    public static final String DS_HOST_ORIG = "http://192.168.0.59:12345";

    public static final String TOKEN = "811e7cb2b21973a1f849dcbe204dd508";
    public static final String DS_HOST = "http://192.168.0.16:12345";

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
                .target(CreateNamespace.class, DS_HOST);

        CreateNamespace.CreateNamespaceResp resp = createNamespace.create(Collections.singletonMap(
                "token",
                TOKEN
        ), "feign-demo", "it's nice.");
        System.out.println(resp);
    }

    static void funcDeleteNamespace() {
        DeleteNamespace deleteNamespace = Feign.builder()
                .decoder(new GsonDecoder())
                .target(DeleteNamespace.class, DS_HOST);

        DeleteNamespace.DeleteNamespaceResp resp = deleteNamespace.delete(Collections.singletonMap(
                "token",
                TOKEN
        ), ImmutableMap.of(
                "projectId", 2
        ));
        System.out.println(resp);
    }

    static void funcImportDefinition() {
        ImportDefinition importDefinition = Feign.builder()
                .encoder(new FormEncoder())
                .decoder(new GsonDecoder())
                .target(ImportDefinition.class, DS_HOST);

        ImportDefinition.ImportDefinitionResp resp = importDefinition.importDefinition(Collections.singletonMap(
                "token",
                TOKEN
        ), "feign-demo", Paths.get("dag01.json").toFile());
        System.out.println(resp);
    }

    static void funcListDefinitions() {
        ListDefinition listDefinition = Feign.builder()
                .decoder(new GsonDecoder())
                .target(ListDefinition.class, DS_HOST);

        ListDefinition.ListDefinitionResp resp = listDefinition.listDefinitions(Collections.singletonMap(
                "token",
                TOKEN
        ), "feign-demo");
        System.out.println(resp);
    }

    static void funcMarkDefinition() {
        MarkInstanceStatus markInstanceStatus = Feign.builder()
                .encoder(new FormEncoder())
                .decoder(new GsonDecoder())
                .target(MarkInstanceStatus.class, DS_HOST);

        MarkInstanceStatus.MarkInstanceStatusResp resp = markInstanceStatus.mark(Collections.singletonMap(
                "token",
                TOKEN
        ), "feign-demo", 2, 1);
        System.out.println(resp);
    }

    static void funcConfigDefinitionSchedule() {
        ConfigDefinitionSchedule sched = Feign.builder()
                .encoder(new FormEncoder())
                .decoder(new GsonDecoder())
                .target(ConfigDefinitionSchedule.class, DS_HOST);

        ConfigDefinitionSchedule.ConfigDefinitionScheduleResp resp = sched.sched(Collections.singletonMap(
                        "token",
                        TOKEN
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
                        .processDefinitionId("2")
                        .build()
        );
        System.out.println(resp);
    }

    static void listSnapshot() {
        ListSnapshot listSnapshot = Feign.builder()
                .decoder(new GsonDecoder())
                .target(ListSnapshot.class, DS_HOST);
        ListSnapshot.ListSnapshotResp resp = listSnapshot.snapshots(
                Collections.singletonMap(
                        "token",
                        TOKEN
                ),
                "feign-demo",
                ImmutableMap.of(
                        "processDefinitionId", "2",
                        "searchVal", "",
                        "pageNo", "0",
                        "pageSize", "1"
                )
        );
        System.out.println(resp);
    }

    static void funcManageInstanceStart() {
        ManageInstance manageInstance = Feign.builder()
                .encoder(new FormEncoder())
                .decoder(new GsonDecoder())
                .target(ManageInstance.class, DS_HOST);
        ManageInstance.ManageInstanceResp resp = manageInstance.start(
                Collections.singletonMap(
                        "token",
                        TOKEN
                ),
                "feign-demo",
                ImmutableMap.of("id", "1")
        );
        System.out.println(resp);
    }

    static void funcManageInstanceStop() {
        ManageInstance manageInstance = Feign.builder()
                .encoder(new FormEncoder())
                .decoder(new GsonDecoder())
                .target(ManageInstance.class, DS_HOST);
        ManageInstance.ManageInstanceResp resp = manageInstance.stop(
                Collections.singletonMap(
                        "token",
                        TOKEN
                ),
                "feign-demo",
                ImmutableMap.of("id", "1")
        );
        System.out.println(resp);
    }

    static void startOnce() {
        ManageInstance manageInstance = Feign.builder()
                .encoder(new FormEncoder())
                .decoder(new GsonDecoder())
                .target(ManageInstance.class, DS_HOST);
        ManageInstance.ManageInstanceResp resp = manageInstance.startOnce(
                Collections.singletonMap(
                        "token",
                        TOKEN
                ),
                "feign-demo",
                ManageInstance.DolphinScheduler.builder()
                        .processDefinitionId("2")
                        .scheduleTime("")
                        .failureStrategy("END")
                        .warningType("NONE")
                        .warningGroupId("0")
                        .execType("")
                        .startNodeList("")
                        .taskDependType("TASK_POST")
                        .runMode("RUN_MODE_SERIAL")
                        .processInstancePriority("MEDIUM")
                        .receivers("")
                        .receiversCc("")
                        .workerGroup("default")
                        .build()
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
//        listSnapshot();
//        funcConfigDefinitionSchedule();
//        funcManageInstanceStart();
//        startOnce();
        funcManageInstanceStop();
    }

}
