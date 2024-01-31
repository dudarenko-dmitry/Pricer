package pl.senla.pricer.exception;

public class UserProfileNotFoundException extends RuntimeException {

    public UserProfileNotFoundException(Long id) {
        super("Could not found UserProfile with ID " + id);
    }

}
