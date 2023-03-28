package com.kmuttfood.orderfood.api;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.ResourceEditorRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
@Configuration
@Order(1)
public class AdminSecurityConfig implements WebMvcConfigurer {
    // @Autowired
    // private DataSource dataSource;

    @Bean
    public UserDetailsService userDetailsService() {
        return new CutomerUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path MenuUploadDir = Paths.get("/menu-image/");
        String MenuUploadPath = MenuUploadDir.toFile().getAbsolutePath();
        registry.addResourceHandler("/menu-image/**").addResourceLocations("file:/" + MenuUploadPath + "/");
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain1(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() // ถ้า api /login อนุญาตให้เข้าถึงได้
                .antMatchers("/").permitAll();

        http
                .antMatcher("/admin/**")
                .authorizeRequests().anyRequest().hasAuthority("Admin")

                .and()
                .formLogin()
                .loginPage("/admin/login").permitAll()
                .loginProcessingUrl("/admin/login")
                .defaultSuccessUrl("/admin/welcome", true)
                .usernameParameter("email").permitAll()
                .and()
                .logout().logoutUrl("/admin/logout")
                .logoutSuccessUrl("/admin/login");
        return http.build();

    }

}
