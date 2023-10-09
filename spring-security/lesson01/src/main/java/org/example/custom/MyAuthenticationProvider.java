package org.example.custom;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class MyAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = String.valueOf(authentication.getPrincipal());
        String password = String.valueOf(authentication.getCredentials());

        System.out.println(username + ":" + password);
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // 认证的提供者得告诉调用方支持哪些凭证, 不然就不会被使用
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
