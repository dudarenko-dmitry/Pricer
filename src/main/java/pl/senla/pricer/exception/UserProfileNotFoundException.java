package pl.senla.pricer.exception;

public class UserProfileNotFoundException extends RuntimeException {

    public UserProfileNotFoundException(Long id) {
        throw new NullPointerException("Could not found UserProfile with ID " + id);
    }

}
