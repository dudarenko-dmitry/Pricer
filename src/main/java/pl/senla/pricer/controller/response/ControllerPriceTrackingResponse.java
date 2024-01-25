package pl.senla.pricer.controller.response;

import org.springframework.web.bind.annotation.RequestParam;
import pl.senla.pricer.dto.PriceTrackingDto;

import java.time.LocalDate;
import java.util.Map;

public interface ControllerPriceTrackingResponse extends ControllerCRUDAllResponse<PriceTrackingDto> {

    Map<LocalDate, Integer> getPriceDynamic(@RequestParam Map<String, String> requestParams);
}
