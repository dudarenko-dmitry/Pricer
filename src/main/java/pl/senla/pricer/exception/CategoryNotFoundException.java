package pl.senla.pricer.exception;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(Long id) {
        super("Could not found Category with ID " + id);
    }

}
