package pl.senla.pricer.exception;

public class CategoryByIdNotFoundException extends RuntimeException {

    public CategoryByIdNotFoundException(Long id) {
        super("Could not found Category with ID " + id);
    }

}
