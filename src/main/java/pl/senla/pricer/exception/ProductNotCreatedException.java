package pl.senla.pricer.exception;

public class ProductNotCreatedException extends RuntimeException {

    public ProductNotCreatedException() {
        super("Could not create Product.");
    }

}
