package com.example.demo.rest;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class HelloService {
    /**
     *  http://localhost:8080/hello?name=sabo
     *
     * @param name
     * @return
     */
    @RequestMapping(value = "/hello", method = GET)
    public String hello(@RequestParam String name) {
        return "Hello world: " + name;
    }

    /**
     * http://localhost:8080/hello/rick
     *
     * @param name
     * @return
     */
    @RequestMapping(value = "/hello/{name}", method = GET)
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
    @SuperBuilder
    static class Task {
        private int id;
        private String title;
        private String description;
        private String status;
    }

    @GetMapping("/tasks")
    public List<Task> findTask() {
        return Arrays.asList(
                Task.builder().id(10000).title("t10000").description("des 10000").status("In Progress").build(),
                Task.builder().id(10000).title("t10001").description("des 10001").status("In Progress").build(),
                Task.builder().id(10000).title("t10002").description("des 10002").status("In Progress").build()
        );
    }

    @Data
    private static class RequstBean {
        private String name;
    }

    // nacos part

    @NacosInjected
    private NamingService namingService;

    @RequestMapping(value = "/get", method = GET)
    public List<Instance> get(@RequestParam String serviceName) throws NacosException {
        return namingService.getAllInstances(serviceName);
    }

}
