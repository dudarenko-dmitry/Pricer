package pl.senla.pricer.exception;

public class ShopNotFoundException extends RuntimeException {

    public ShopNotFoundException(Long id) {
        super("Could not found Shop with ID " + id);
    }

}
