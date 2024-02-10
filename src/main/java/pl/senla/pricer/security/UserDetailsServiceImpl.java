package pl.senla.pricer.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.senla.pricer.dao.DaoUser;
import pl.senla.pricer.entity.User;
import pl.senla.pricer.utils.UserDetailsConverter;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private DaoUser daoUser;

    @Override
    public UserDetails loadUserByUsername(String username) {
        log.debug("UserDetailsServiceImpl: loadUserByUsername");
        try {
            User user = daoUser.findByUsername(username);
            return UserDetailsConverter.convertUserToUserDetails(user);
        } catch (UsernameNotFoundException e) {
            log.info("User doesn't exist\n" + e);
            return null;
        }
    }
}
