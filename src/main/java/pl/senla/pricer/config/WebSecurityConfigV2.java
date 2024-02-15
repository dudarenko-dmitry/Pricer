package pl.senla.pricer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import pl.senla.pricer.filter.CustomAuthorizationFilter;
import pl.senla.pricer.security.UserDetailsServiceImpl;

import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfigV2 {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests((auth) -> auth
                                .requestMatchers("/", "/auth/login", "/auth/registration", "/v2/users/registration", "/auth/registration/v2", "/auth/logout").permitAll()
                                .requestMatchers("/v2/**").authenticated()
                                .requestMatchers("/admin/**").hasAuthority("admin:write").anyRequest().authenticated()
//                        .requestMatchers(HttpMethod.POST, "/v2/**").hasAuthority(Permission.WRITE.getPermission())
//                        .requestMatchers(HttpMethod.GET, "/v2/**").hasAuthority(Permission.READ.getPermission())
//                        .requestMatchers(HttpMethod.POST, "/admin").hasAuthority(Permission.ADMIN_WRITE.getPermission())
//                        .requestMatchers(HttpMethod.PUT, "/v2/**").hasAuthority(Permission.UPDATE.getPermission())
//                        .requestMatchers(HttpMethod.DELETE, "/v2/**").hasAuthority(Permission.DELETE.getPermission())
//                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/auth/login").permitAll())
                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout", "POST"))
                        .permitAll()
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/auth/login"))
                .authenticationProvider(daoAuthenticationProvider())
                .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return new ProviderManager(Collections.singletonList(daoAuthenticationProvider()));
    }

}
