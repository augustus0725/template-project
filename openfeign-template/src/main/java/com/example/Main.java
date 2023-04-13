package com.example;

import com.example.feign.MyApi;
import feign.Feign;
import feign.gson.GsonDecoder;

public class Main {
    public static void main(String[] args) {
        MyApi myApi = Feign.builder()
                .decoder(new GsonDecoder())
                .target(MyApi.class, "https://www.juhe.cn");

        MyApi.Keyword keyword = myApi.keyword();
        System.out.println(keyword);
    }
}
