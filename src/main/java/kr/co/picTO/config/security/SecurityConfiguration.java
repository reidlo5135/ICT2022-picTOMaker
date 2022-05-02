package kr.co.picTO.config.security;

import kr.co.picTO.advice.exception.RestAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final LocalUserJwtProvider localUserJwtProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/css/**", "/static/js/**", "/static/img/**", "/static/lib/**", "/static/media/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .formLogin().disable()
                    .httpBasic().disable()
                    .addFilterBefore(new LocalUserJwtAuthenticationFilter(localUserJwtProvider), UsernamePasswordAuthenticationFilter.class)
                    .exceptionHandling()
                        .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .and()
                    .authorizeRequests()
                        .antMatchers("/",
                            "/error",
                            "/favicon.ico",
                            "/**/*.png",
                            "/**/*.gif",
                            "/**/*.svg",
                            "/**/*.jpg",
                            "/**/*.html",
                            "/**/*.css",
                            "/**/*.js",
                            "/**/*.json",
                            "/**/*.otf",
                            "/**/content.js.map",
                            "/requestProvider.js.map").permitAll()
                    .antMatchers(HttpMethod.POST, "/v1/signUp").permitAll()
                    .antMatchers(HttpMethod.GET,"/exception/**", "/v1/login").permitAll()
                    .antMatchers("/oauth2/**", "/", "/social/login", "/social/login/**", "/account/**", "/api/**", "/Info").permitAll()
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
                    .formLogin().disable()
                    .oauth2Login().userInfoEndpoint();

    }
}
