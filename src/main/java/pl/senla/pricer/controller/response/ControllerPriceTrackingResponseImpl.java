package pl.senla.pricer.controller.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.senla.pricer.dto.PriceTrackingDto;
import pl.senla.pricer.entity.PriceTracking;
import pl.senla.pricer.exception.PriceTrackingByIdNotFoundException;
import pl.senla.pricer.exception.PriceTrackingNotCreatedException;
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
            if (list.isEmpty()) {
                return new ResponseEntity<>("list is empty", HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(list.toString(), HttpStatus.OK);
        } catch (RuntimeException e) {
            log.warn(e.toString());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @PostMapping("/")
    public ResponseEntity<String> create(@RequestBody PriceTrackingDto priceTrackingDto) {
        log.debug("ControllerProduct 'Create'");
        try {
            PriceTracking priceTracking = servicePriceTracking.create(priceTrackingDto);
            if (priceTracking != null) {
                PriceTrackingDto priceTrackingDtoNew = PriceTrackerDtoConverter
                    .convertToDto(priceTracking);
                return new ResponseEntity<>(priceTrackingDtoNew.toString(), HttpStatus.CREATED);
            }
            return new ResponseEntity<>(new PriceTrackingNotCreatedException().toString(), HttpStatus.OK);
        } catch (RuntimeException e) {
            log.warn(e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            return new ResponseEntity<>(new PriceTrackingNotCreatedException().toString(), HttpStatus.OK);

        }
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<String> read(@PathVariable Long id) {
        log.debug("ControllerProduct 'Read'");
        try {
            PriceTracking priceTracking = servicePriceTracking.read(id);
            if (priceTracking != null) {
                PriceTrackingDto priceTrackingDto = PriceTrackerDtoConverter
                        .convertToDto(priceTracking);
                return new ResponseEntity<>(priceTrackingDto.toString(), HttpStatus.OK);
            }
            return new ResponseEntity<>(new PriceTrackingByIdNotFoundException(id).toString(), HttpStatus.OK);
        } catch (RuntimeException e) {
            log.warn(e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody PriceTrackingDto priceTracking) {
        log.debug("ControllerProduct 'Update'");
        try {
            PriceTracking priceTrackingUpdate = servicePriceTracking.update(id, priceTracking);
            if (priceTrackingUpdate != null) {
                PriceTrackingDto priceTrackingDtoUpdate = PriceTrackerDtoConverter
                        .convertToDto(priceTrackingUpdate);
                return new ResponseEntity<>(priceTrackingDtoUpdate.toString(), HttpStatus.OK);
            }
            return new ResponseEntity<>(new PriceTrackingNotCreatedException().toString(), HttpStatus.OK);
        } catch (RuntimeException e) {
            log.warn(e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        log.debug("ControllerProduct 'Delete'");
        try {
            if (servicePriceTracking.read(id) != null) {
                servicePriceTracking.delete(id);
                return new ResponseEntity<>("PriceTracking id " + id + " was deleted.", HttpStatus.OK);
            }
            return new ResponseEntity<>(new PriceTrackingByIdNotFoundException(id).toString(), HttpStatus.OK);
        } catch (RuntimeException e) {
            log.warn(e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @GetMapping("/dynamic")
    public ResponseEntity<String> getPriceDynamic(@RequestParam Map<String, String> requestParams) {
        log.debug("ControllerProduct 'getPriceDynamic'");
        if (requestParams.isEmpty()) {
            log.info("Not enough parameters in get-request for this method.");
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        try {
            String productName = requestParams.get("product_name");
            log.info("Price dynamic for {}:", productName);
            Map<LocalDate, Integer> priceDynamic = servicePriceTracking.getPriceDynamic(requestParams);
            return new ResponseEntity<>(priceDynamic.toString(), HttpStatus.OK);
        } catch (RuntimeException e) {
            log.warn(e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
