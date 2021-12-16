package com.example.demo.auth;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author crazy
 * date: 2020/8/2
 */
@Component
public class HTTPBearerAuthorizeAttribute implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        if (null != req.getHeader("Authorization")) {
           // check
            return;
        }
        if (req.getRequestURI().endsWith("/hello/rick")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        // Not authed
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Your request if not a authorized.");
    }

    public static void main(String[] args) {
        System.out.println("Bearer xxxxx".substring(7));
    }
}
