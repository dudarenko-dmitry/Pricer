package pl.senla.pricer.exception;

public class PriceNotFoundException extends RuntimeException {

    public PriceNotFoundException(Long id) {
        super("Could not found Price with ID " + id);
    }

}
