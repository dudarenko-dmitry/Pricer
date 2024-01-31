package pl.senla.pricer.exception;

public class PriceTrackingByIdNotFoundException extends RuntimeException {

    public PriceTrackingByIdNotFoundException(Long id) {
        super("Could not found PriceTracking with ID " + id);
    }

}
