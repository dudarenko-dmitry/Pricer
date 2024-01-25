package pl.senla.pricer.controller.rest;

import org.springframework.web.bind.annotation.RequestParam;
import pl.senla.pricer.dto.PriceTrackingDto;

import java.time.LocalDate;
import java.util.Map;

public interface ControllerPriceTracking extends ControllerCRUDAll<PriceTrackingDto> {

    Map<LocalDate, Integer> getPriceDynamic(@RequestParam Map<String, String> requestParams);
}
