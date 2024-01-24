package pl.senla.pricer.exception;

import pl.senla.pricer.dto.UserProfileDto;

public class UserProfileNotCreatedException extends RuntimeException {

    public UserProfileNotCreatedException(UserProfileDto userProfileDto) {
        super("Could not create UserProfile: " + userProfileDto.toString());
    }

}
