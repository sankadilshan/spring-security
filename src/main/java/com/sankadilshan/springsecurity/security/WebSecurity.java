package com.sankadilshan.springsecurity.security;

import com.sankadilshan.springsecurity.auth.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.concurrent.TimeUnit;

import static com.sankadilshan.springsecurity.enums.Permissions.*;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ApplicationUserService applicationUserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                .and()
                .csrf().disable()
                .authorizeRequests()
//                .antMatchers("/login").permitAll()
                .antMatchers("/api/v1/health").permitAll()
                .antMatchers("/api/v1").hasAnyAuthority(ADMIN_READ.getPermission(),ADMIN_WRITE.getPermission())
                .antMatchers("/api/v1/*/student").hasAnyAuthority(STUDENT_READ.getPermission(), ADMIN_READ.getPermission())
                .anyRequest()
                .authenticated()
                .and()
//                .httpBasic()       /// http basic authentication  no logout
                .formLogin()
                     .loginPage("/login")
                     .permitAll()
                     .defaultSuccessUrl("/index",true)
                     .usernameParameter("username")
                     .passwordParameter("password")
                .and()
                .rememberMe()
                      .tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(21))
                      .key("securekey")
                      .rememberMeParameter("remember-me")
                .and()
                .logout()
                     .logoutUrl("/logout")
//                     .logoutRequestMatcher(new AntPathRequestMatcher("/logout","GET")) // if your enable CSRF and you need to handle logout with get method
                     .clearAuthentication(true)
                     .invalidateHttpSession(true)
                     .deleteCookies("JSESSIONID","remember-me")
                     .logoutSuccessUrl("/login");
    }

//    @Override
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user_student = User.builder()
//                .username("student")
//                .password(passwordEncoder.encode("user"))
////                .roles(STUDENT_LEAD.name())
////                .authorities(STUDENT_READ.getPermission(), STUDENT_WRITE.getPermission())
//                .authorities(STUDENT_LEAD.getGrantedAuthorities())
//                .build();
//
//        UserDetails user_admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder.encode("user"))
////                .roles(ADMIN.name())
////                .authorities(ADMIN_READ.name(),ADMIN_WRITE.getPermission())
//                .authorities(ADMIN.getGrantedAuthorities())
//                .build();
//
//        UserDetails user_admin_write = User.builder()
//                .username("admin_write")
//                .password(passwordEncoder.encode("user"))
//                .roles(ADMIN.name())
////                .authorities(ADMIN_WRITE.getPermission())
//                .authorities(ADMIN_W.getGrantedAuthorities())
//                .build();
//
//        return new InMemoryUserDetailsManager(user_student, user_admin, user_admin_write);
//    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(applicationUserService);
        return daoAuthenticationProvider;
    }



}
