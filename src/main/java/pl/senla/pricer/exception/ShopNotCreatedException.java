package pl.senla.pricer.exception;

public class ShopNotCreatedException extends RuntimeException {

    public ShopNotCreatedException() {
        super("Could not create Shop.");
    }

}
