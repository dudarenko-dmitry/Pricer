package pl.senla.pricer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {

    private Long user_id;
    private String firstName;
    private String lastName;

}
