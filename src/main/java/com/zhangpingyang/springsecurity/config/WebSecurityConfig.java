package com.zhangpingyang.springsecurity.config;

import com.zhangpingyang.springsecurity.filter.JwtFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtFilter jwtFilter;
    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers(HttpMethod.POST, "/auth")
                .and()
                .ignoring()
                .antMatchers(HttpMethod.GET, "/index")
                .and()
                .ignoring()
                .antMatchers(HttpMethod.GET,
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js")
                .and()
        .ignoring()
        .antMatchers(HttpMethod.GET, "/index")
        ;
//        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
        http.csrf().disable()
                //TODO exceptionHandling
                //不适用session
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
                //auth开头的路径随意访问
        .antMatchers("/auth/**").permitAll()
        .antMatchers("/index").permitAll()

        //其他任意的请求需要权限
        .anyRequest().authenticated();

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        http.headers()
                .frameOptions().sameOrigin()//// X-frame-options  https://zhidao.baidu.com/question/502193450915313244.html
                .cacheControl();
//http.addFilterBefore()
//        ;
    }
}
