package com.storm.test.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.storm.test.user.Permission.*;
import static com.storm.test.user.Permission.MANAGER_DELETE;
import static com.storm.test.user.Role.ADMIN;
import static com.storm.test.user.Role.MANAGER;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtAuthentificationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                //.cors()
                //.and()
                //.cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth ->auth
                        .requestMatchers(
                                "/api/v1/auth/**",
                                "/v2/api-docs",
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/swagger-resources",
                                "/swagger-resources/**",
                                "/configuration/ui",
                                "/configuration/security",
                                "/swagger-ui/**",
                                "/webjars/**",
                                "/swagger-ui.html"
                        )
                        .permitAll()
                        .requestMatchers("api/v1/sections/**").hasAnyRole(ADMIN.name(),MANAGER.name())
                        .requestMatchers("api/v1/products/**").hasAnyRole(ADMIN.name(),MANAGER.name())


                      /*  .requestMatchers(HttpMethod.GET,"api/v1/sections").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
                        .requestMatchers(HttpMethod.POST,"api/v1/sections").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
                        .requestMatchers(HttpMethod.PUT,"api/v1/sections").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
                        .requestMatchers(HttpMethod.DELETE,"api/v1/sections").hasAuthority(ADMIN_DELETE.name())

                        .requestMatchers("api/v1/products").hasAnyRole(ADMIN.name(),MANAGER.name())


                        .requestMatchers(HttpMethod.GET,"api/v1/products").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
                        .requestMatchers(HttpMethod.POST,"api/v1/products").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
                        .requestMatchers(HttpMethod.PUT,"api/v1/products").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
                        .requestMatchers(HttpMethod.DELETE,"api/v1/products").hasAuthority(MANAGER_DELETE.name())*/
                        .anyRequest()
                        .authenticated()
                )

                .sessionManagement(sess ->sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
/*public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/token/**").permitAll()
            .anyRequest().authenticated()
        )
        .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
        .httpBasic(Customizer.withDefaults())
        .build();
}*/