package com.getbridge.homework.api.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
class HttpSecurityConfig {
    @Bean
    @Throws(Exception::class)
    fun configure(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests { r ->
                r.requestMatchers(HttpMethod.GET, "/health/200Ok").permitAll()

                r.requestMatchers(HttpMethod.POST, "/api/v1/one-on-one/").permitAll()
                r.requestMatchers(HttpMethod.DELETE, "/api/v1/one-on-one/**").permitAll()
                r.requestMatchers(HttpMethod.GET, "/api/v1/one-on-one/**").permitAll()
                r.requestMatchers(HttpMethod.PUT, "/api/v1/one-on-one/**").permitAll()
                r.requestMatchers(HttpMethod.PATCH, "/api/v1/one-on-one/{id:\\d+}/conclude").permitAll()

                r.anyRequest().denyAll()
            }

        return http.build()
    }
}
