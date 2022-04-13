package kr.co.picTO.config.security;

import kr.co.picTO.service.security.BaseCustomOAuth2UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final BaseCustomOAuth2UserService customOAuth2UserService;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/v1/sign/signup", "/v1/sign/login", "/v1/sign/reissue", "/v1/sign/social/**").permitAll()
                    .antMatchers(HttpMethod.GET,"/exception/**").permitAll()
                    .antMatchers("/login/oauth2/code/**").permitAll()
                    .antMatchers("/oauth2/**", "/", "/social/login", "/social/login/**").permitAll()
                    .antMatchers("/index").permitAll()
                    .mvcMatchers("/v3/api-docs/**",
                        "/configuration/**",
                        "/swagger*/**",
                        "/webjars/**",
                        "/swagger-resources/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .logout()
                        .logoutSuccessUrl("/logout")
                        .deleteCookies()
                .and()
                    .oauth2Login()
                        .defaultSuccessUrl("/social/login/{provider}")
                        .userInfoEndpoint()
                            .userService(customOAuth2UserService);

    }
}
