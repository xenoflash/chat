package com.lf.chat.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;

@EnableOAuth2Sso
@Configuration
public class OAuth2Configuration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
	http.antMatcher("/**").authorizeRequests().antMatchers("/", "/login**", "/user", "/webjars/**", "/error**")
		.permitAll().anyRequest().authenticated();

	http.sessionManagement().invalidSessionUrl("/login").maximumSessions(1).sessionRegistry(sessionRegistry()).expiredUrl("/login");
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
}
