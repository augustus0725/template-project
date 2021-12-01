package com.example.demo.rest;

import lombok.Data;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloService {
    /**
     *  http://localhost:8080/hello?name=sabo
     *  @RequestParam(required = false) 当参数可有可没有的时候
     * @param name
     * @return
     */
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(@RequestParam String name) {
        return "Hello world: " + name;
    }

    /**
     * http://localhost:8080/hello/rick
     *
     * @param name
     * @return
     */
    @RequestMapping(value = "/hello/{name}", method = RequestMethod.GET)
    public String helloViaPath(@PathVariable String name) {
        return "Hello world: " + name;
    }

    /**
     * curl --location --request POST 'http://127.0.0.1:8080/hello' \
     * --header 'Content-Type: application/json' \
     * --data-raw '{
     * 	"name": "sabo"
     * }'
     *
     * @param requstBean
     * @return
     */
    @PostMapping("/hello")
    public String helloViaBody(@RequestBody RequstBean requstBean) {
        return "hello world: " + requstBean.getName();
    }

    @Data
    private static class RequstBean {
        private String name;
    }
}
