package org.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class MyWebEntrySecurityConfiguration extends WebSecurityConfigurerAdapter {
    /**
     *  配置这个来改变对  entry(比如 /hello ) 的安全控制规则
     */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
        http.httpBasic(); /* 通过检查header里的basic来认证 */
        http.authorizeRequests().anyRequest().authenticated(); /* 所有的请求都要认证 */
//        http.authorizeRequests().anyRequest().permitAll(); /* 允许所有的请求 */
    }
}
