package pl.senla.pricer.utils;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import pl.senla.pricer.entity.User;

@Component
public final class UserDetailsConverter {

    private UserDetailsConverter() {

    }

    public static UserDetails convertUserToUserDetails(User user) {
//        return org.springframework.security.core.userdetails.User.builder()
//                .username(user.getUsername())
//                .password(user.getPassword())
//                .roles(String.valueOf(user.getRole().getAuthorities()))
//                .build();
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getIsEnabled(),
                user.getIsEnabled(),
                user.getIsEnabled(),
                user.getIsEnabled(),
                user.getRole().getAuthorities()
        );
    }
}
