package pl.senla.pricer.exception;

public class UserProfileNotCreatedException extends RuntimeException {

    public UserProfileNotCreatedException() {
        super("Could not create UserProfile");
    }

}
