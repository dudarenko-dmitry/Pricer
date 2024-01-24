package pl.senla.pricer.controller.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.senla.pricer.dto.UserProfileDto;
import pl.senla.pricer.entity.UserProfile;
import pl.senla.pricer.exception.UserProfileNotCreatedException;
import pl.senla.pricer.exception.UserProfileNotFoundException;
import pl.senla.pricer.service.ServiceUserProfile;
import pl.senla.pricer.utils.UserProfileDtoConverter;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("v2/user_profiles")
@Slf4j
public class ControllerUserProfileResponseImpl implements ControllerUserProfileResponse {

    @Autowired
    private ServiceUserProfile serviceUserProfile;

    @Override
    @GetMapping
    public ResponseEntity<String> readAll(@RequestParam Map<String, String> requestParams) {
        log.debug("ControllerUserProfile 'ReadAll'");
        try {
            List<UserProfileDto> list = serviceUserProfile.readAll(requestParams).stream()
                    .map(UserProfileDtoConverter::convertProfileToDto)
                    .toList();
            return new ResponseEntity<>(list.toString(), HttpStatus.OK);
        } catch (UserProfileNotFoundException e) {
            return new ResponseEntity<>("list is empty", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @PostMapping("/")
    public ResponseEntity<String> create(@RequestBody UserProfileDto userProfileDto) {
        log.debug("ControllerUserProfile 'Create'");
        try {
            UserProfile userProfile = serviceUserProfile.create(userProfileDto);
            return new ResponseEntity<>(userProfile.toString(), HttpStatus.CREATED);
        } catch (UserProfileNotCreatedException e) {
            return new ResponseEntity<>("UserProfile is already exists", HttpStatus.FORBIDDEN);
        }
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<String> read(@PathVariable Long id) {
        log.debug("ControllerUserProfile 'Read'");
        try {
            UserProfileDto userProfileDto = UserProfileDtoConverter.convertProfileToDto(serviceUserProfile.read(id));
            return new ResponseEntity<>(userProfileDto.toString(), HttpStatus.OK);
        } catch (UserProfileNotFoundException e) {
            return new ResponseEntity<>("UserProfile not found", HttpStatus.NO_CONTENT);
        }
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody UserProfileDto userProfileDto) {
        log.debug("ControllerUserProfile 'Update'");
        try {
            UserProfileDto userProfileDtoUpdate = UserProfileDtoConverter
                    .convertProfileToDto(serviceUserProfile.update(id, userProfileDto));
            return new ResponseEntity<>(userProfileDto.toString(), HttpStatus.OK);
        } catch (UserProfileNotCreatedException | UserProfileNotFoundException e) {
            return new ResponseEntity<>("UserProfile not found", HttpStatus.NO_CONTENT);
        }
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        log.debug("ControllerUserProfile 'Delete'");
        try {
            serviceUserProfile.delete(id);
            return new ResponseEntity<>("UserProfile was deleted.", HttpStatus.OK);
        } catch (UserProfileNotFoundException e) {
            return new ResponseEntity<>("UserProfile not found", HttpStatus.NO_CONTENT);
        }
    }
}
