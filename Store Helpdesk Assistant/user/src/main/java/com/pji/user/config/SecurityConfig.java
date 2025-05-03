package com.pji.user.config;


import com.pji.user.service.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtFilter jwtFilter;
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
                http.sessionManagement().sessionCreationPolicy(STATELESS);
                http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/")
                .permitAll()
                .antMatchers(HttpMethod.DELETE, "/")
                .permitAll()
                .antMatchers(HttpMethod.PUT, "/")
                .permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .permitAll()
//                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll();
                

        http.headers().frameOptions().disable();

     //   http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

//    public void configure(HttpSecurity http) throws Exception {
//
//        http.csrf().disable().authorizeRequests().antMatchers("/user/login").permitAll().antMatchers(HttpMethod.OPTIONS)
//
//                .permitAll()
//                .antMatchers(HttpMethod.GET, "/user").hasRole("ADMIN")
//                .antMatchers(HttpMethod.GET, "/user/list").hasAnyRole("MANAGER", "ADMIN","HELPDESK")
//                .authenticated();
//
//    }
//}


//  http
//          .authorizeRequests()
//          .antMatchers("/store/**").hasAnyRole("ADMIN", "MANAGER", "HELPDESK")
//          .antMatchers("/store/add", "/store/delete", "/store/update").hasRole("ADMIN")
//          .antMatchers( "/parameter/add", "/parameter/delete", "/parameter/update").hasRole("ADMIN")
//          .antMatchers("/store/upgrade").hasRole("HELPDESK")
//          .anyRequest().authenticated()
//          .and()
//          .formLogin()
//          .permitAll()
//          .and()
//          .logout()
//          .permitAll();
//          }


//.and() .formLogin();-
// can add uname and pwd and login directly.
// will be redirected to http:localhost:8081/login