package pl.senla.pricer.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super("Could not found User with ID " + id);
    }

}
