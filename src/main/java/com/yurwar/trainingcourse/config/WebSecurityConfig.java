//package com.yurwar.trainingcourse.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//
//import javax.sql.DataSource;
//
////@Configuration
////@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
////    @Bean
////    @Override
////    public UserDetailsService userDetailsService() {
////        UserDetails userDetails = User
////                        .withDefaultPasswordEncoder()
////                        .username("user@gmail.com")
////                        .password("password")
////                        .roles("USER")
////                        .build();
////        return new InMemoryUserDetailsManager(userDetails);
////    }
//
//    private final DataSource dataSource;
//
//    @Autowired
//    public WebSecurityConfig(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }
//
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .passwordEncoder(NoOpPasswordEncoder.getInstance())
//                .usersByUsernameQuery("select email, password from registered_users where email=?")
//                .authoritiesByUsernameQuery("select u.email, ur.roles from registered_users u inner join user_role ur on u.id = ur.user_id where u.email = ?");
//
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();
//
//        http
//                .authorizeRequests()
//                    .antMatchers("/index", "/js/*", "/css/*", "/login", "/registration-form").permitAll()
//                    .anyRequest()
//                    .authenticated()
//                .and()
//                    .formLogin()
//                    .loginPage("/login")
//                    .permitAll()
//                .and()
//                    .logout()
//                    .permitAll();
//    }
//}
