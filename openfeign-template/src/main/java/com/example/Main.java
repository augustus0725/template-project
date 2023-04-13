package com.example;

import com.example.feign.CreateNamespace;
import com.example.feign.DeleteNamespace;
import com.example.feign.MyApi;
import com.google.common.collect.ImmutableMap;
import feign.Feign;
import feign.form.FormEncoder;
import feign.gson.GsonDecoder;

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
        ),ImmutableMap.of(
                "projectId", 12
        ));
        System.out.println(resp);
    }

    static void funcImportDefinition() {

    }

    public static void main(String[] args) {
//        funcCreateNamespace();
        funcDeleteNamespace();
    }
}
