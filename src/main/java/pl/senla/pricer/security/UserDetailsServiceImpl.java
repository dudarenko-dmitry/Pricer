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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("UserDetailsServiceImpl: loadUserByUsername");
        User user = daoUser.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User doesn't exist\n");
        }
        return UserDetailsConverter.convertUserToUserDetails(user);
    }
}
