package com.company.glovo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.NullRequestCache;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.formLogin(AbstractAuthenticationFilterConfigurer::permitAll).sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers(HttpMethod.GET, "/**").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, "/orders/").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.PUT, "/orders/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/orders/**").hasRole("ADMIN").and()
                .requestCache().requestCache(new NullRequestCache()).and()
                .logout(LogoutConfigurer::permitAll)
                .httpBasic(Customizer.withDefaults())
                .cors().and().
                csrf().disable().build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        System.out.println(encoder.encode("qwerty123!"));
        System.out.println(encoder.encode("password"));
        boolean isMatchPassword = encoder.matches("qwerty123!", "{bcrypt}$2a$10$XvH4Y1z2btWqqWNWRmf1keND8w1TZiKib1luUxABUEPzY/z3wybIe");
        System.out.println(isMatchPassword);

        UserDetails user = User
                .withUsername("user")
                .password("{bcrypt}$2a$10$zvNek7XUwB8LALYk6xHwN.9Xh/RPTFGdT4.kjRSXCbwsEKnPshFxa")
                .roles("USER")
                .build();

        UserDetails admin = User
                .withUsername("admin")
                .password("{bcrypt}$2a$10$XvH4Y1z2btWqqWNWRmf1keND8w1TZiKib1luUxABUEPzY/z3wybIe")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}