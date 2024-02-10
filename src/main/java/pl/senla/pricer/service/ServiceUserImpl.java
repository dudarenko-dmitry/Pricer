package pl.senla.pricer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.senla.pricer.dao.DaoUser;
import pl.senla.pricer.dto.UserDto;
import pl.senla.pricer.entity.User;
import pl.senla.pricer.exception.UserNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class ServiceUserImpl implements ServiceUser {

    @Autowired
    private DaoUser daoUser;

    @Override
    public List<User> readAll(Map<String, String> requestParams) {
        log.debug("Start ServiceUser 'Create'");
        List<User> users = daoUser.findAll();
        if (users.isEmpty()) {
            log.debug("List of Users is empty.");
        }
        return users;
    }

    @Override
    public User create(UserDto userDto) {
        log.debug("Start ServiceUser 'Create'");
        String username = userDto.getUsername();
        boolean isPresent = readAll(null).stream()
                .anyMatch(u -> u.getUsername().equals(username));
        if (!isPresent) {
            User userNew = new User();
            userNew.setUsername(username);
            userNew.setPassword(userDto.getPassword());
            userNew.setIsEnabled(true);
            return daoUser.save(userNew);
        }
        log.info("This User is already exists.");
        return null;
    }

    @Override
    public User read(Long id) {
        log.debug("Start ServiceUser 'Read by ID'");
        return daoUser.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public User update(Long id, UserDto userDto) {
        log.debug("Start ServiceUser 'Update'");
        Optional<User> userUpdate = daoUser.findById(id);
        if (userUpdate.isPresent()) {
            User userNew = userUpdate.get();
//            userNew.setUsername(userDto.getUsername());
//            userNew.setPassword(userDto.getPassword());
//            userNew.setIsEnabled(userDto.getIsEnabled());
            return daoUser.save(userNew);
        }
        log.info("User not found.");
        return null;
    }

    @Override
    public void delete(Long id) {
        log.debug("Start ServiceUser 'Delete'");
        log.info("Application doesn't have functionality to delete User.");
    }
}