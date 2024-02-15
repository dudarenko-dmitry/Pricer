package pl.senla.pricer.utils;

import pl.senla.pricer.dto.UserDto;
import pl.senla.pricer.entity.User;

public final class UserDtoConverter {

    private UserDtoConverter() {
    }

    public static UserDto convertUserToDto(User user) {
        return new UserDto(user.getUsername(), user.getPassword());
    }
}
