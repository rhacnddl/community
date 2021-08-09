package org.gorany.community.config;

import lombok.extern.log4j.Log4j2;
import org.gorany.community.security.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
@Log4j2
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.headers().frameOptions().sameOrigin();

        http.csrf().disable();
        http.cors().and().csrf().disable().authorizeRequests().antMatchers("/stomp/**", "/chat/**").permitAll();

        /*http.authorizeRequests().antMatchers("/board/**").authenticated()
                                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                                .anyRequest().permitAll();*/

        //인가/인증에 문제시 로그인 화면
        http.rememberMe().tokenValiditySeconds(60 * 60 * 24 * 3).userDetailsService(userDetailsService); //3days
        http.formLogin().loginPage("/login").loginProcessingUrl("/login_post").defaultSuccessUrl("/index");
        http.logout();
    }

/*    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication().withUser("user1").
                password("$2a$10$ukBFQHHAYVqw1dBCoKzou.77mK/FFmFudor1/qBXzsO.mJMj3Ffka").roles("USER");
    }*/
}