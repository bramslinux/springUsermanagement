package com.example.userManagement.security;
import com.example.userManagement.service.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class Securityconf {
    private final JwtService jwtService;
    public Securityconf(JwtService jwtService){
        this.jwtService=jwtService;
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http){

        http
                .csrf(csrf->csrf.disable())
                .sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.ALWAYS.STATELESS))
                .authorizeHttpRequests(auth->auth.requestMatchers("/auth/**").permitAll().anyRequest().authenticated())
                .addFilterBefore(new Jwtfilter(jwtService),UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }

}
