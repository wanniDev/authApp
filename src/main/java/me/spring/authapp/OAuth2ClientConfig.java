package me.spring.authapp;

import me.spring.authapp.service.OAuth2UserRegistrationRegistrationService;
import me.spring.authapp.service.OidcUserRegistrationRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class OAuth2ClientConfig {

    @Autowired
    private OAuth2UserRegistrationRegistrationService customOAuth2UserService;
    @Autowired
    private OidcUserRegistrationRegistrationService customOidcUserService;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/static/js/**", "/static/images/**", "/static/css/**","/static/scss/**");
    }

    @Bean
    SecurityFilterChain oauth2SecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(customizer ->
                customizer
                        .requestMatchers("/api/user").hasAnyRole("SCOPE_profile", "SCOPE_email")
                        .requestMatchers("/api/oidc").hasAnyRole("SCOPE_openid")
                        .requestMatchers("/").permitAll()
                        .anyRequest().authenticated()
        );

        http.oauth2Login(oauth2 -> oauth2.userInfoEndpoint(
                userInfoEndpointConfig -> userInfoEndpointConfig
                        .userService(customOAuth2UserService)
                        .oidcUserService(customOidcUserService)));
        http.logout(logout -> logout.logoutSuccessUrl("/"));
        return http.build();
    }

    @Bean
    public GrantedAuthoritiesMapper customAuthorityMapper(){
        return new CustomAuthorityMapper();
    }
}
