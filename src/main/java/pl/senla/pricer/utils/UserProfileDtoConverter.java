package pl.senla.pricer.utils;

import org.springframework.stereotype.Component;
import pl.senla.pricer.dto.UserProfileDto;
import pl.senla.pricer.entity.UserProfile;

@Component
public final class UserProfileDtoConverter {

    private UserProfileDtoConverter() {

    }

    public static UserProfileDto convertProfileToDto (UserProfile userProfile) {
        return UserProfileDto.builder()
                .firstName(userProfile.getFirstName())
                .lastName(userProfile.getLastName())
                .build();
    }

}
