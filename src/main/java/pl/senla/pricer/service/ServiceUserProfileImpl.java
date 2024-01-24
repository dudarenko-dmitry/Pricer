package pl.senla.pricer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.senla.pricer.dao.DaoUserProfile;
import pl.senla.pricer.dto.UserProfileDto;
import pl.senla.pricer.entity.UserProfile;
import pl.senla.pricer.exception.UserProfileNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class ServiceUserProfileImpl implements ServiceUserProfile {

    @Autowired
    private DaoUserProfile daoUserProfile;

    @Override
    public List<UserProfile> readAll(Map<String, String> requestParams) {
        log.debug("Start ServiceUserProfile 'ReadAll'");
        List<UserProfile> profiles = daoUserProfile.findAll();
        if (profiles.isEmpty()) {
            log.debug("List of UserProfiles is empty.");
        }
        return profiles;
    }

    @Override
    public UserProfile create(UserProfileDto userProfileDto) {
        log.debug("Start ServiceUserProfile 'Create'");
        String firstName = userProfileDto.getFirstName();
        String lastName = userProfileDto.getLastName();
        UserProfile userProfile = readAll(null).stream()
                .filter(p -> p.getFirstName().equals(firstName)
                        && p.getLastName().equals(lastName))
                .findAny()
                .orElse(null);
        if (userProfile == null) {
            log.debug("Create new UserProfile");
            UserProfile userProfileNew = new UserProfile();
            userProfileNew.setFirstName(firstName);
            userProfileNew.setLastName(lastName);
            return daoUserProfile.save(userProfileNew);
        }
        log.debug("UserProfile is already exists");
        return null;
    }

    @Override
    public UserProfile read(Long id) {
        log.debug("Start ServiceUserProfile 'Read by ID'");
        return daoUserProfile.findById(id)
                .orElseThrow(() -> new UserProfileNotFoundException(id));
    }

    @Override
    public UserProfile update(Long id, UserProfileDto userProfileDto) {
        log.debug("Start ServiceUserProfile 'Update'");
        Optional<UserProfile> userProfile = daoUserProfile.findById(id);
        if (userProfile.isPresent()) {
            UserProfile userProfileUpdate = userProfile.get();
            userProfileUpdate.setFirstName(userProfileDto.getFirstName());
            userProfileUpdate.setLastName(userProfileDto.getLastName());
            return daoUserProfile.save(userProfileUpdate);
        }
        log.debug(String.valueOf(new UserProfileNotFoundException(id)));
        return null;
    }

    @Override
    public void delete(Long id) {
        log.debug("Start ServiceUserProfile 'Delete by ID'");
        if (!daoUserProfile.existsById(id)) {
            log.debug(String.valueOf(new UserProfileNotFoundException(id)));
        } else {
            log.debug("UserProfile was deleted.");
            daoUserProfile.deleteById(id);
        }
    }
}
