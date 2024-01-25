package pl.senla.pricer.exception;

public class CategoryNotCreatedException extends RuntimeException {

    public CategoryNotCreatedException() {
        super("Could not create Category.");
    }

}
