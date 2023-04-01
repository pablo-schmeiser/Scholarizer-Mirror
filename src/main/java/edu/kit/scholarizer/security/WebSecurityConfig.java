package edu.kit.scholarizer.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * This class configures the security of the application.
 * It is used to define which endpoints are accessible by which users.
 * It also defines the login page.
 * The login page and all other areas accessible by everyone are defined in the defaultFilterChain method.
 * The admin area is defined in the adminFilterChain method.
 * The user area is defined in the userFilterChain method.
 * @author Pablo Schmeiser
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
@Profile("!https")
public class WebSecurityConfig {

    /**
     * This method defines the default security filter chain.
     * @param http the http security
     * @return the security filter chain
     * @throws Exception if an error occurs
     */
    @Bean
    public SecurityFilterChain defaultFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests().anyRequest().permitAll();
        return http.build();
    }

}
