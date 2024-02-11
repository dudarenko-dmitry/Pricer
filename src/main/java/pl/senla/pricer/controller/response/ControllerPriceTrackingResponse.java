package pl.senla.pricer.controller.response;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import pl.senla.pricer.dto.PriceTrackingDto;

import java.util.Map;

public interface ControllerPriceTrackingResponse extends ControllerCRUDAllResponse<PriceTrackingDto> {

    @PostMapping("/load/")
    ResponseEntity<String> createFromFile(@RequestBody String filePath);

    ResponseEntity<String> getPriceDynamic(@RequestParam Map<String, String> requestParams);
}
