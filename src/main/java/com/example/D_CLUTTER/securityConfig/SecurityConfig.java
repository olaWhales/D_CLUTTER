package com.example.D_CLUTTER.securityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;
@Configuration
@EnableWebSecurity
public class SecurityConfig {

@Autowired
private JwtFilter jwtFilter  ;

@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, JwtFilter jwtFilter)throws Exception{
    httpSecurity
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(requests -> requests
                    .requestMatchers("/User/sign_up/" ,"/User/login/").permitAll()
                    .requestMatchers("/User/changePassword/").permitAll()
                    .requestMatchers("/admin/registration/").permitAll()
                    .requestMatchers("/product/upload/").permitAll()
                    .anyRequest().authenticated())
            .httpBasic(Customizer.withDefaults())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtFilter , UsernamePasswordAuthenticationFilter.class)
            .cors(cors -> cors.configurationSource(request -> {
                var config = new CorsConfiguration();
                config.setAllowedOrigins(List.of("http://localhost:5173")); // Frontend URL
                config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
                config.setAllowCredentials(true);
                return config;
            }));
//            .cors(cors -> cors.configurationSource(corsConfigurationSource())); // Use method for CORS

    return httpSecurity.build();
}

//private CorsFilter corsFilter() {
//    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//    CorsConfiguration config = new CorsConfiguration();
//    config.setAllowCredentials(true);
//    config.addAllowedOriginPattern("*");
//    config.addAllowedHeader("*");
//    config.addAllowedMethod("*");
//    source.registerCorsConfiguration("/**", config);
//    return new CorsFilter(source);
//}

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173", "http://172.16.0.179:8080")); // Add External IP
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        config.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

@Bean
public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService){
    DaoAuthenticationProvider provide = new DaoAuthenticationProvider();
    provide.setPasswordEncoder(new BCryptPasswordEncoder(12));
    provide.setUserDetailsService(userDetailsService);
    return provide;
}
@Bean
public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)throws Exception{
    return configuration.getAuthenticationManager();
}
@Bean
public BCryptPasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
}
}

