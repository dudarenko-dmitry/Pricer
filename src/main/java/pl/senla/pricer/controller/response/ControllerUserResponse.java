package pl.senla.pricer.controller.response;

import org.springframework.http.ResponseEntity;
import pl.senla.pricer.dto.UserDto;

public interface ControllerUserResponse extends ControllerCRUDAllResponse<UserDto>{

    ResponseEntity<String> createAdmin(UserDto userDto);

}
