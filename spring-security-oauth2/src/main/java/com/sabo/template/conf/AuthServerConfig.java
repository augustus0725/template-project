package com.sabo.template.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 * @author canbin.zhang
 * date: 2020/2/15
 */
@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Value("${user.oauth.clientId}")
    private String ClientID;
    @Value("${user.oauth.clientSecret}")
    private String ClientSecret;
    @Value("${user.oauth.redirectUris}")
    private String RedirectURLs;

    @Override
    public void configure(
            AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    @Override
    /**
     *  TODO: If we need numbers of clients.
     *
     *  clients.jdbc(dataSource());
     *
     *  create table oauth_client_details (
     *     client_id VARCHAR(256) PRIMARY KEY,
     *     resource_ids VARCHAR(256),
     *     client_secret VARCHAR(256),
     *     scope VARCHAR(256),
     *     authorized_grant_types VARCHAR(256),
     *     web_server_redirect_uri VARCHAR(256),
     *     authorities VARCHAR(256),
     *     access_token_validity INTEGER,
     *     refresh_token_validity INTEGER,
     *     additional_information VARCHAR(4096),
     *     autoapprove VARCHAR(256)
     *   );
     */
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // clients.jdbc(dataSource());
        clients.inMemory()
                .withClient(ClientID)
                .secret(ClientSecret)
                .authorizedGrantTypes("authorization_code")
                .scopes("user_info")
                .autoApprove(true)
                .redirectUris("http://www.baidu.com", "https://www.sina.com.cn/");
    }
}
