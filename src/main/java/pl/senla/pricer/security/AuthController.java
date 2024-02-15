package pl.senla.pricer.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.senla.pricer.dto.UserDto;

import java.util.Collections;

@Slf4j
@Controller
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto userDto) {

        AuthenticationManager authenticationManager = new ProviderManager(Collections.singletonList(daoAuthenticationProvider()));
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok("User authenticated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }
    }

    @GetMapping("/login")
    public String getLoginPage() {
        log.debug("Test login");
        return "login";
    }

    @GetMapping("/main")
    public String getMainPage() {
        log.debug("Test main");
        return "main";
    }

    @GetMapping("/registration")
    public String createRegistrationUser() {
        log.debug("Test registration");
        return "registration";
    }

    private AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        return daoAuthenticationProvider;
    }

    private UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    private PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
