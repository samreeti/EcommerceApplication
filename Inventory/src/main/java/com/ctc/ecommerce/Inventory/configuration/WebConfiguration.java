package com.ctc.ecommerce.Inventory.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebConfiguration extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                .antMatchers("/")
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/console/**")
                .permitAll()
                .and()
                .httpBasic();
        httpSecurity.csrf().disable();
        httpSecurity.headers().frameOptions().disable();
    }
}