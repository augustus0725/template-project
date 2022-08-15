package com.example.demo.config;

import org.springframework.security.web.context.SecurityContextPersistenceFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @author zhangcanbin@hongwangweb.com
 * @date 2022/7/9 15:52
 */
public class MySecurityContextPersistenceFilter extends SecurityContextPersistenceFilter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        System.out.println("测试下新增一个同类型的Filter会不会覆盖之前的");
        super.doFilter(req, res, chain);
    }
}
