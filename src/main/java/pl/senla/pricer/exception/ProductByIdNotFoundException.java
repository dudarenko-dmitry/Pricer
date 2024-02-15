package pl.senla.pricer.exception;

public class ProductByIdNotFoundException extends RuntimeException {

    public ProductByIdNotFoundException(Long id) {
        throw new NullPointerException("Could not found Product with ID " + id);
    }

}
