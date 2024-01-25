package pl.senla.pricer.controller.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.senla.pricer.dto.PriceTrackingDto;
import pl.senla.pricer.exception.PriceTrackingNotCreatedException;
import pl.senla.pricer.exception.PriceTrackingNotFoundException;
import pl.senla.pricer.service.ServicePriceTracking;
import pl.senla.pricer.utils.PriceTrackerDtoConverter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("v2/price_tracking")
@Slf4j
public class ControllerPriceTrackingResponseImpl implements ControllerPriceTrackingResponse {

    @Autowired
    private ServicePriceTracking servicePriceTracking;

    @Override
    @GetMapping
    public ResponseEntity<String> readAll(@RequestParam Map<String, String> requestParams) {
        log.debug("ControllerProduct 'ReadAll'");
        try {
            List<PriceTrackingDto> list = servicePriceTracking.readAll(requestParams).stream()
                    .map(PriceTrackerDtoConverter::convertToDto)
                    .toList();
            return new ResponseEntity<>(list.toString(), HttpStatus.OK);
        } catch (PriceTrackingNotFoundException e) {
            log.debug(e.toString());
            return new ResponseEntity<>("PriceTracking not found.", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @PostMapping("/")
    public ResponseEntity<String> create(@RequestBody PriceTrackingDto priceTrackingDto) {
        log.debug("ControllerProduct 'Create'");
        try {
            PriceTrackingDto priceTrackingDtoNew = PriceTrackerDtoConverter
                    .convertToDto(servicePriceTracking.create(priceTrackingDto));
            return new ResponseEntity<>(priceTrackingDtoNew.toString(), HttpStatus.CREATED);
        } catch (PriceTrackingNotCreatedException e) {
            log.debug(e.toString());
            return new ResponseEntity<>("PriceTracking not created.", HttpStatus.FORBIDDEN);
        }
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<String> read(@PathVariable Long id) {
        log.debug("ControllerProduct 'Read'");
        try {
            PriceTrackingDto priceTrackingDto = PriceTrackerDtoConverter.convertToDto(servicePriceTracking.read(id));
            return new ResponseEntity<>(priceTrackingDto.toString(), HttpStatus.OK);
        } catch (PriceTrackingNotFoundException e) {
            log.debug(e.toString());
            return new ResponseEntity<>("PriceTracking not found.", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody PriceTrackingDto priceTrackingDto) {
        log.debug("ControllerProduct 'Update'");
        try {
            PriceTrackingDto priceTrackingDtoUpdate = PriceTrackerDtoConverter
                    .convertToDto(servicePriceTracking.update(id, priceTrackingDto));
            return new ResponseEntity<>(priceTrackingDtoUpdate.toString(), HttpStatus.OK);
        } catch (PriceTrackingNotCreatedException | PriceTrackingNotFoundException e) {
            log.debug(e.toString());
            return new ResponseEntity<>("PriceTracking not updated.", HttpStatus.FORBIDDEN);
        }
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        log.debug("ControllerProduct 'Delete'");
        try {
            servicePriceTracking.delete(id);
            return new ResponseEntity<>("PriceTracking was deleted.", HttpStatus.OK);
        } catch (PriceTrackingNotFoundException e) {
            log.debug(e.toString());
            return new ResponseEntity<>("PriceTracking not found.", HttpStatus.NOT_FOUND);
        }

    }

    @Override
    @GetMapping("/dynamic")
    public Map<LocalDate, Integer> getPriceDynamic(@RequestParam Map<String, String> requestParams) {
        log.debug("ControllerProduct 'getPriceDynamic'");
        if (requestParams.isEmpty()) {
            log.warn("Not enough parameters in get-request for this method.");
            return null;
        }
        String productName = requestParams.get("product_name");
        log.info("Price dynamic for {}:", productName);
        return servicePriceTracking.getPriceDynamic(requestParams);
    }
}
