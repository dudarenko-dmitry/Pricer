package pl.senla.pricer.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String name) {
        throw new NullPointerException("Could not found Product with name: " + name);
    }

}
