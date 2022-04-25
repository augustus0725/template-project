package com.sabo;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import io.minio.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println(UUID.randomUUID());

        MinioClient client = MinioClient.builder()
                .endpoint("http://192.168.0.181:9000")
                .credentials("dxp", "Windows_7")
                .build();

        if (!client.bucketExists(BucketExistsArgs.builder().bucket("dxp").build())) {
            client.makeBucket(MakeBucketArgs.builder()
                    .bucket("dxp")
                    .build());
        }

//        client.uploadObject(UploadObjectArgs.builder()
//                .bucket("dxp")
//                .object("objectname")
//                .filename("a.rar")
//                .build());


//        client.downloadObject(
//                DownloadObjectArgs.builder()
//                        .bucket("dxp")
//                        .object("objectname")
//                        .filename("b.rar")
//                        .build()
//        );

        // 保存字符串
        /*
        byte[] bytes = "hello".getBytes(StandardCharsets.UTF_8);
        client.putObject(PutObjectArgs.builder()
                .bucket("dxp")
                .object("string-hello")
                .stream(new ByteArrayInputStream(bytes), bytes.length, -1)
                .build());

        try (InputStream inputStream = client.getObject(GetObjectArgs.builder()
                .bucket("dxp")
                .object("string-hello")
                .build()
        )) {
            System.out.println(CharStreams.toString(new InputStreamReader(inputStream, Charsets.UTF_8)));
        }
        */

        // 保存中文

        byte[] bytes = "中文".getBytes(StandardCharsets.UTF_8);
        client.putObject(PutObjectArgs.builder()
                .bucket("dxp")
                .object("string-chinese")
                .stream(new ByteArrayInputStream(bytes), bytes.length, -1)
                .build());

        try (InputStream inputStream = client.getObject(GetObjectArgs.builder()
                .bucket("dxp")
                .object("string-chinese")
                .build()
        )) {
            System.out.println(CharStreams.toString(new InputStreamReader(inputStream, Charsets.UTF_8)));
        }
    }
}
