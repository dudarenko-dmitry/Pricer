package pl.senla.pricer.controller.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.senla.pricer.dto.UserProfileDto;
import pl.senla.pricer.entity.UserProfile;
import pl.senla.pricer.exception.UserProfileNotFoundException;
import pl.senla.pricer.service.ServiceUserProfile;
import pl.senla.pricer.utils.UserProfileDtoConverter;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user_profiles")
@Slf4j
public class ControllerUserProfileRest implements ControllerUserProfile {

    @Autowired
    private ServiceUserProfile serviceUserProfile;

    @Override
    @GetMapping
    public List<UserProfileDto> readAll(@RequestParam Map<String, String> requestParams) {
        log.debug("ControllerUserProfile 'ReadAll'");
        return serviceUserProfile.readAll(requestParams).stream()
                .map(UserProfileDtoConverter::convertProfileToDto)
                .toList();
    }

    @GetMapping("/test")
    public ResponseEntity<String> readAll1(@RequestParam Map<String, String> requestParams) {
        log.debug("ControllerUserProfile 'ReadAll'");
        List<UserProfileDto> list = serviceUserProfile.readAll(requestParams).stream()
                .map(UserProfileDtoConverter::convertProfileToDto)
                .toList();
        if(list.isEmpty()) {
            return new ResponseEntity<>("list is empty", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list.toString(), HttpStatus.OK);
    }

    @Override
    @PostMapping("/")
    public UserProfileDto create(@RequestBody UserProfileDto userProfileDto) {
        log.debug("ControllerUserProfile 'Create'");
        UserProfile userProfile = serviceUserProfile.create(userProfileDto);
        if (userProfile != null) {
            return UserProfileDtoConverter.convertProfileToDto(serviceUserProfile.create(userProfileDto));
        }
        log.warn("UserProfiles was not created.");
        throw new UserProfileNotFoundException(1L);
    }

    @PostMapping("/test/")
    public ResponseEntity<String> create1(@RequestBody UserProfileDto userProfileDto) {
        log.debug("ControllerUserProfile 'Create'");
        UserProfile userProfile = serviceUserProfile.create(userProfileDto);
        if (userProfile != null) {
            return new ResponseEntity<>(userProfile.toString(), HttpStatus.CREATED);
        }
        log.warn("UserProfiles was not created.");
        return new ResponseEntity<>("UserProfile is already exists", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    @GetMapping("/{id}")
    public UserProfileDto read(@PathVariable Long id) {
        log.debug("ControllerUserProfile 'Read'");
        return UserProfileDtoConverter.convertProfileToDto(serviceUserProfile.read(id));
    }

    @Override
    @PutMapping("/{id}")
    public UserProfileDto update(@PathVariable Long id, @RequestBody UserProfileDto userProfileDto) {
        log.debug("ControllerUserProfile 'Update'");
        return UserProfileDtoConverter.convertProfileToDto(serviceUserProfile.update(id, userProfileDto));
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.debug("ControllerUserProfile 'Delete'");
        serviceUserProfile.delete(id);
    }
}
