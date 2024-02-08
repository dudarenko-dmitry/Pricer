package pl.senla.pricer.controller.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.senla.pricer.dto.UserDto;
import pl.senla.pricer.entity.User;
import pl.senla.pricer.exception.UserNotCreatedException;
import pl.senla.pricer.exception.UserNotFoundException;
import pl.senla.pricer.service.ServiceUser;

import java.util.List;
import java.util.Map;

@RestController
//@RequestMapping("/v2/users")
@Slf4j
public class ControllerUserResponseImpl implements ControllerUserResponse {

    @Autowired
    private ServiceUser serviceUser;

    @Override
//    @PreAuthorize("hasAuthority(ADMIN')")
//    @Secured("ADMIN")
    @GetMapping("/users")
    public ResponseEntity<String> readAll(@PathVariable Map<String, String> requestParams) {
        log.debug("ControllerUser 'ReadAll'");
        try {
            List<User> users = serviceUser.readAll(requestParams);
            return new ResponseEntity<>(users.toString(), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("List of Users is empty", HttpStatus.NO_CONTENT);
        }
    }

    @Override
    @PostMapping("/registration")
    public ResponseEntity<String> create(@RequestBody UserDto userDto) {
        log.debug("ControllerUser 'Create'");
        try {
            User userNew = serviceUser.create(userDto);
            if (userNew != null) {
                return new ResponseEntity<>(userNew.toString(), HttpStatus.CREATED);
            }
            return new ResponseEntity<>("User is already exist in DB.\n" + new UserNotCreatedException(),
                    HttpStatus.OK);
        } catch (UserNotCreatedException e) {
            log.warn(e.toString());
            return new ResponseEntity<>(new UserNotCreatedException().toString(), HttpStatus.BAD_REQUEST) ;
        }
    }

    @Override
    @GetMapping("/users/{id}")
    public ResponseEntity<String> read(@PathVariable Long id) {
        log.debug("ControllerUser 'Read'");
        try {
            User user = serviceUser.read(id);
            if (user != null) {
                return new ResponseEntity<>(user.toString(), HttpStatus.OK);
            }
            return new ResponseEntity<>(new UserNotFoundException(id).toString(), HttpStatus.OK);
        } catch (RuntimeException e) {
            log.warn(e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @PutMapping("/users/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody UserDto userDto) {
        log.debug("ControllerUser 'Update'");
        try {
            User userUpdate = serviceUser.update(id, userDto);
            if (userUpdate != null) {
                return new ResponseEntity<>(userUpdate.toString(), HttpStatus.OK);
            }
            return new ResponseEntity<>(new UserNotCreatedException().toString(), HttpStatus.OK);
        } catch (RuntimeException e) {
            log.warn(e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        log.debug("ControllerUser 'Delete'");
        try {
            if (serviceUser.read(id) != null) {
                serviceUser.delete(id);
                return new ResponseEntity<>("Application doesn't have functionality to delete User.",
                        HttpStatus.OK);
            }
            return new ResponseEntity<>(new UserNotFoundException(id).toString(), HttpStatus.OK);
        } catch (RuntimeException e) {
            log.warn(e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
