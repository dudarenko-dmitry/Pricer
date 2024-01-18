package pl.senla.pricer.exception;

public class ShopByIdNotFoundException extends RuntimeException {

    public ShopByIdNotFoundException(Long id) {
        super("Could not found Shop with ID " + id);
    }

}
