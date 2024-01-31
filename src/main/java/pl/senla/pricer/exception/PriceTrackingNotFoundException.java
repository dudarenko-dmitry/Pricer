package pl.senla.pricer.exception;

import java.time.LocalDate;

public class PriceTrackingNotFoundException extends RuntimeException {

    public PriceTrackingNotFoundException(LocalDate date) {
        super("Could not found Price for this date " + date);
    }

}
