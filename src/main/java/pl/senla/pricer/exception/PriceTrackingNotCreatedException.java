package pl.senla.pricer.exception;

public class PriceTrackingNotCreatedException extends RuntimeException {

    public PriceTrackingNotCreatedException() {
        super("Could not create Price.");
    }

}
