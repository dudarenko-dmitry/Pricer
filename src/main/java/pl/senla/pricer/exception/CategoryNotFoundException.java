package pl.senla.pricer.exception;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(Long id) {
        throw new NullPointerException("Could not found Category with ID " + id);
    }

}
