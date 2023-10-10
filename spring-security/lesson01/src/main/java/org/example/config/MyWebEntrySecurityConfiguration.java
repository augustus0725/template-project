package org.example.config;

import org.example.custom.MyAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class MyWebEntrySecurityConfiguration extends WebSecurityConfigurerAdapter {
    /**
     * 配置这个来改变对  entry(比如 /hello ) 的安全控制规则
     */

//    @Override
    protected void configure2(HttpSecurity http) throws Exception {
//        super.configure(http);
        http.httpBasic(c -> {
            c.authenticationEntryPoint(new AuthenticationEntryPoint() {
                @Override
                public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                    // 配置错误响应的header
                    response.addHeader("message", "Your request is unauthorized.");
                    response.sendError(HttpStatus.UNAUTHORIZED.value());
                }
            });
        }); /* 通过检查header里的basic来认证 */
        http.authorizeRequests().anyRequest().authenticated(); /* 所有的请求都要认证 */
//        http.authorizeRequests().anyRequest().permitAll(); /* 允许所有的请求 */
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
        // 添加自定义Filter, 指定位置
        http.addFilterBefore((servletRequest, servletResponse, filterChain) -> {
            //
            System.out.println("Before BasicAuthenticationFilter");
            filterChain.doFilter(servletRequest, servletResponse);
        }, BasicAuthenticationFilter.class);

        http.formLogin()
                /*
                .successHandler((request, response, authentication) -> {
                    System.out.println("登录成功之后跳转");
                    System.out.println("ContextPath: " + request.getContextPath());
                    System.out.println("ServletPath: " + request.getServletPath());
                    System.out.println("PathTranslated: " + request.getPathTranslated());
                    System.out.println("PathInfo: " + request.getPathInfo());
                }).failureHandler((request, response, exception) -> {
                    System.out.println("登录失败之后跳转");
                })
                 */
        ;
        http.authorizeRequests().anyRequest().authenticated(); /* 所有的请求都要认证 */
    }

//    @Autowired
//    private MyAuthenticationProvider provider;

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        super.configure(auth);
////        auth.authenticationProvider(provider);
//    }
}
