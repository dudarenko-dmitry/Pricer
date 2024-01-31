package pl.senla.pricer.controller.response;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import pl.senla.pricer.dto.PriceTrackingDto;

import java.util.Map;

public interface ControllerPriceTrackingResponse extends ControllerCRUDAllResponse<PriceTrackingDto> {

    ResponseEntity<String> getPriceDynamic(@RequestParam Map<String, String> requestParams);
}
