package kr.co.picto.common.configuration;

import kr.co.picto.common.exception.CustomAccessDeniedHandler;
import kr.co.picto.common.exception.RestAuthenticationEntryPoint;
import kr.co.picto.token.application.JwtAuthenticationFilter;
import kr.co.picto.token.application.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtProvider jwtProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/exception/**", "/v1/api/picto/**/**/**", "/v1/api/community/**/**").permitAll()
                .antMatchers(HttpMethod.POST, "/v1/api/oauth2/**/**/**", "/v1/api/user/**", "/v1/api/picto/**/**/**", "/v1/api/qna/**/**", "/v1/api/community/**/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/v1/api/picto/**/**/**", "/v1/api/user/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/v1/api/oauth2/**", "/v1/api/user/**", "/v1/api/picto/delete").permitAll()
                .antMatchers("/v1/api/users/**", "/v1/api/oauth/**").permitAll()
                .antMatchers("/oauth2/redirect/**", "/").permitAll()
                .antMatchers("/index").permitAll()
                .mvcMatchers("/v3/api-docs/**",
                        "/configuration/**",
                        "/swagger*/**",
                        "/webjars/**",
                        "/swagger-resources/**").permitAll()
                .antMatchers("/v1/api/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated();

    }
}
