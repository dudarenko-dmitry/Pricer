package pl.senla.pricer.controller.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('admin:write')")
    @GetMapping
    public ResponseEntity<String> readAll(@RequestParam Map<String, String> requestParams) {
        log.debug("ControllerUserProfile 'ReadAll'");
        try {
            List<UserProfileDto> list = serviceUserProfile.readAll(requestParams).stream()
                    .map(UserProfileDtoConverter::convertProfileToDto)
                    .toList();
            if (list.isEmpty()) {
                return new ResponseEntity<>("list is empty", HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(list.toString(), HttpStatus.OK);
        } catch (RuntimeException e) {
            log.warn(e.toString());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @PreAuthorize("hasAuthority('write')")
//    @PreAuthorize("#username == authentication.principal.username")
    @PostMapping("/")
    public ResponseEntity<String> create(@RequestBody UserProfileDto userProfileDto) {
        log.debug("ControllerUserProfile 'Create'");
        try {
            UserProfile userProfileNew = serviceUserProfile.create(userProfileDto);
            if (userProfileNew != null) {
                UserProfileDto userProfileDtoNew = UserProfileDtoConverter
                        .convertProfileToDto(userProfileNew);
                return new ResponseEntity<>(userProfileDtoNew.toString(), HttpStatus.CREATED);
            }
            return new ResponseEntity<>(new UserProfileNotCreatedException().toString(), HttpStatus.OK);
        } catch (RuntimeException e) {
            log.warn(e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @PreAuthorize("hasAuthority('read')")
//    @PreAuthorize("#username == authentication.principal.username")
    @GetMapping("/{id}")
    public ResponseEntity<String> read(@PathVariable Long id) {
        log.debug("ControllerUserProfile 'Read'");
        try {
            UserProfile userProfile = serviceUserProfile.read(id);
            if (userProfile != null) {
                UserProfileDto userProfileDto = UserProfileDtoConverter
                        .convertProfileToDto(userProfile);
                return new ResponseEntity<>(userProfileDto.toString(), HttpStatus.OK);
            }
            return new ResponseEntity<>(new UserProfileNotFoundException(id).toString(), HttpStatus.OK);
        } catch (RuntimeException e) {
            log.warn(e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @PreAuthorize("hasAuthority('admin:write')")
//    @PreAuthorize("#username == authentication.principal.username")
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody UserProfileDto userProfile) {
        log.debug("ControllerUserProfile 'Update'");
        try {
            UserProfile userProfileUpdate = serviceUserProfile.update(id, userProfile);
            if (userProfileUpdate != null) {
                UserProfileDto userProfileDtoUpdate = UserProfileDtoConverter
                        .convertProfileToDto(userProfileUpdate);
                return new ResponseEntity<>(userProfileDtoUpdate.toString(), HttpStatus.OK);
            }
            return new ResponseEntity<>(new UserProfileNotCreatedException().toString(), HttpStatus.OK);
        } catch (RuntimeException e) {
            log.warn(e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @PreAuthorize("hasAuthority('admin:write')")
//    @PreAuthorize("#username == authentication.principal.username")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        log.debug("ControllerUserProfile 'Delete'");
        try {
            if (serviceUserProfile.read(id) != null) {
                serviceUserProfile.delete(id);
                return new ResponseEntity<>("UserProfile id " + id + " was deleted.", HttpStatus.OK);
            }
            return new ResponseEntity<>(new UserProfileNotFoundException(id).toString(), HttpStatus.OK);
        } catch (RuntimeException e) {
            log.warn(e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
