package com.example.feign;

import feign.Headers;
import feign.RequestLine;

public interface MyApi {
    public static class Keyword {
        private Integer code;
        private String reason;
        private String result;

        @Override
        public String toString() {
            return "Keyword{" +
                    "code=" + code +
                    ", reason='" + reason + '\'' +
                    ", result='" + result + '\'' +
                    '}';
        }
    }

    @RequestLine("GET /agent/common/default/keyword")
    @Headers("Accept: application/json;charset=UTF-8")
    Keyword keyword();
}
