package ignitis_homework.security.config;

import ignitis_homework.security.filter.JwtAuthorizationFilter;
import ignitis_homework.security.service.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class JwtSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final JwtProvider jwtProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable();
        http.headers().frameOptions().disable();

        http = http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        // set authorization request access
        http = http
                .authorizeRequests()
                .antMatchers(
                        "/login","/h2-console/**"
                ).permitAll()
                .antMatchers(
                        "/swagger-ui/**",
                        "/swagger-resources/**",
                        "/v2/api-docs/**",  // if we want to use old swagger version
                        "/v3/api-docs/**").permitAll()
                .anyRequest().authenticated()
                .and();
        // set unauthorized requests exception handler
        http = http
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and();

        // set filters
        http.addFilterBefore(new JwtAuthorizationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService);
    }
}
