package pl.senla.pricer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import pl.senla.pricer.entity.Permission;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((request) -> request
                        .requestMatchers("/", "/auth/login", "/auth/registration")
                                .permitAll()
                        .requestMatchers(HttpMethod.GET, "/v2/**")
                            .hasAuthority(Permission.READ.getPermission())
//                                .hasAnyRole("ADMIN", "REGULAR")
                        .requestMatchers(HttpMethod.POST, "/v2/**")
                            .hasAuthority(Permission.WRITE.getPermission())
//                                .hasAnyRole("ADMIN", "REGULAR")
                        .requestMatchers(HttpMethod.POST, "/admin")
                            .hasAuthority(Permission.ADMIN_WRITE.getPermission())
//                                .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/v2/**")
                            .hasAuthority(Permission.UPDATE.getPermission())
//                                .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/v2/**")
                            .hasAuthority(Permission.DELETE.getPermission())
//                                .hasRole("ADMIN")
                                .anyRequest()
                                .authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/auth/login")
                        .permitAll())
                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout", "POST"))
                        .permitAll()
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/auth/login"))
                .authenticationProvider(daoAuthenticationProvider())
                .httpBasic(withDefaults()); // required for Authentication in Postman
        return http.build();
    }

    @Bean
    protected AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .passwordEncoder(passwordEncoder())
//                .usersByUsernameQuery("SELECT username, password FROM users WHERE username=?")
//                .authoritiesByUsernameQuery("SELECT username, authority FROM authorities WHERE username=?");
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(12);
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

//    @Bean
//    public UserDetailsManager createAdmin(DataSource dataSource) {
//        UserDetails userNew = User.builder()
//                .username("admin1")
//                .password(passwordEncoder().encode("admin1"))
//                .roles("ADMIN")
////                .authorities(Role.ADMIN.getAuthorities())
//                .build();
//        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
//        userDetailsManager.createUser(userNew);
//        return userDetailsManager;
//    }

}
