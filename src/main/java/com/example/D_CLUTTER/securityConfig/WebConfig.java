    package com.example.D_CLUTTER.securityConfig;//package com.example.D_CLUTTER.securityConfig;
//
//    import org.jetbrains.annotations.NotNull;
//    import org.springframework.context.annotation.Bean;
//    import org.springframework.context.annotation.Configuration;
//    import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//    import org.springframework.web.servlet.config.annotation.CorsRegistry;
//
//
//
//    @Configuration
//    public class WebConfig {
//
//        @Bean
//        public WebMvcConfigurer corsConfigurer() {
//            return new WebMvcConfigurer() {
//                @Override
//                public void addCorsMappings(@NotNull CorsRegistry registry) {
//                    registry.addMapping("/**")
//                            .allowedOriginPatterns("*")
//                            .allowedOrigins("http://localhost:5173", "https://dclutter-production-52ee.up.railway.app") // Allow frontend
//    //                        .allowedOriginPatterns("http://localhost:5173" , "http://172.16.0.179:8080")
//                            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//    //                        .allowedHeaders("*", "Authentication", "Content-Type")
//    //                        .allowCredentials(false)
//    //                        .exposedHeaders("Authorization");
//                            .allowedHeaders("*");
//                }
//            };
//        }
//    }
//package com.example.D_CLUTTER.securityConfig;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.List;

    @Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NotNull CorsRegistry registry) {
                registry.addMapping("/") // Allow all endpoints
                        .allowedOrigins("*") // Allow all origins (for testing)   i just added this to 
                        .allowedOrigins("http://localhost:5173", "https://dclutter-production-52ee.up.railway.app") // Allow specific frontend origins
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow specific HTTP methods
                        .allowedHeaders("*") // Allow all headers
                        .allowCredentials(true) // Allow credentials (e.g., cookies, tokens)
                        .exposedHeaders("Authorization"); // Expose custom headers
            }
        };
    }
}