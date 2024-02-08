package pl.senla.pricer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((request) -> request
                        .requestMatchers("/",
                                "/auth/login",
                                "/auth/registration")
                            .permitAll()
                        .requestMatchers(HttpMethod.GET, "/**")
                            .hasAuthority(Permission.READ.getPermission())
                        .requestMatchers(HttpMethod.POST, "/**")
                            .hasAuthority(Permission.WRITE.getPermission())
                        .requestMatchers(HttpMethod.POST, "/admin")
                            .hasAuthority(Permission.ADMIN_WRITE.getPermission())
                        .requestMatchers(HttpMethod.PUT, "/**")
                            .hasAuthority(Permission.UPDATE.getPermission())
                        .requestMatchers(HttpMethod.DELETE, "/**")
                            .hasAuthority(Permission.DELETE.getPermission()
                            )
                        .anyRequest()
                        .authenticated()
                )
                .formLogin((form) -> form
//                        .loginProcessingUrl("/auth/login")
                        .loginPage("/auth/login")
                        .permitAll())
                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout", "POST"))
                        .permitAll()
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/auth/login"));
        return http.build();
    }

//    @Bean // check if work???
//    protected void configureAuthentication(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(daoAuthenticationProvider());
//    }

    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider() {
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
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    //    @Bean
//    public UserDetailsManager createRegularUser(DataSource dataSource) {
//        UserDetails userNew = User.builder()
//                .username("user")
//                .password(passwordEncoder().encode("pass"))
//                .authorities(Role.REGULAR.getAuthorities())
//                .build();
//        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
//        userDetailsManager.createUser(userNew);
//        return userDetailsManager;
//    }

//    @Bean
//    public UserDetailsManager createAdmin(DataSource dataSource) {
//        UserDetails userNew = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("admin"))
//                .authorities(Role.ADMIN.getAuthorities())
//                .build();
//        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
//        userDetailsManager.createUser(userNew);
//        return userDetailsManager;
//    }

}
