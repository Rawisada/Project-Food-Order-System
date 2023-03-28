package com.kmuttfood.orderfood.api;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Order(2)
public class UserSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain2(HttpSecurity http) throws Exception {
        http
                .antMatcher("/user/**")
                .authorizeRequests().anyRequest().hasAuthority("User")
                .and()
                .formLogin()
                .loginPage("/user/login").permitAll()
                .loginProcessingUrl("/user/login")
                .defaultSuccessUrl("/user/welcome", true)
                .usernameParameter("email").permitAll()
                .and()
                .logout().logoutUrl("/user/logout")
                .logoutSuccessUrl("/user/login");
        return http.build();
    }
}
