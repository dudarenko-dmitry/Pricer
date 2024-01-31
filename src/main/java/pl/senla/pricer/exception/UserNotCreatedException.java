package pl.senla.pricer.exception;

public class UserNotCreatedException extends RuntimeException {

    public UserNotCreatedException() {
        super("Could not create User.");
    }

}
