package com.yun.demo.springbootdemo.config.security;

import org.springframework.boot.autoconfigure.security.oauth2.authserver.AuthorizationServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

import javax.annotation.Resource;

@Configuration
@EnableResourceServer
public class OauthResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Resource
    private OAuth2ClientProperties oAuth2ClientProperties;

    @Bean
    public AuthorizationServerProperties authorizationServerProperties() {
        return new AuthorizationServerProperties();
    }

    @Bean
    public ResourceServerTokenServices tokenServices() {
        RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
        remoteTokenServices.setClientId(oAuth2ClientProperties.getClientId());
        remoteTokenServices.setClientSecret(oAuth2ClientProperties.getClientSecret());
        remoteTokenServices.setCheckTokenEndpointUrl(authorizationServerProperties().getCheckTokenAccess());
        return remoteTokenServices;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/auth/**").authenticated()
                .anyRequest().permitAll();
    }

}
