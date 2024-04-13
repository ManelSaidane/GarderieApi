package com.example.garderieapi.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //http.csrf( cerf -> cerf.disable() );
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers("/v1/Auth/**").permitAll()
                                 .requestMatchers("/v1/Admin/**").hasRole("ADMIN")
                                 .requestMatchers("/v1/Garderie/**").hasRole("GARD")
                                 .requestMatchers("/v1/Responsable/**").hasRole("RESPONSABLE")
                                 .requestMatchers("/v1/Parent/**").hasRole("PARENT")
                                 .anyRequest().authenticated()
                );
        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
