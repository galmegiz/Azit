package com.sun.Azit.config;

import com.sun.Azit.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.DispatcherType;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    MemberService memberService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf().disable().cors().disable()
                .authorizeRequests(request -> request
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .mvcMatchers("/images/**",  "/events/**", "/members/**", "/lorahost/**", "/sneat/**", "/img/**").permitAll()
                        .mvcMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                ).formLogin(login -> login
                        .loginPage("/members/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .failureUrl("/")
                        .defaultSuccessUrl("/events", true)
                        .permitAll()
                ).logout(logout -> logout
                        .logoutSuccessUrl("/"));
        return http.build();

    }


    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); };
}
