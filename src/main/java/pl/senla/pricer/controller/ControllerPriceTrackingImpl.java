package pl.senla.pricer.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.senla.pricer.dto.PriceTrackingDto;
import pl.senla.pricer.service.ServicePriceTracking;
import pl.senla.pricer.utils.PriceTrackerDtoConverter;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/price_tracking")
@Slf4j
public class ControllerPriceTrackingImpl implements ControllerPriceTracking {

    @Autowired
    private ServicePriceTracking servicePriceTracking;

    @Override
    @GetMapping
    public List<PriceTrackingDto> readAll(@RequestParam Map<String, String> requestParams) {
        log.debug("ControllerProduct 'ReadAll'");
        if (requestParams.isEmpty()) {
            return servicePriceTracking.readAll().stream()
                    .map(PriceTrackerDtoConverter::convertToDto)
                    .toList();
        }
        return servicePriceTracking.readAllWithParams(requestParams).stream()
                .map(PriceTrackerDtoConverter::convertToDto)
                .toList();
    }

    @Override
    @PostMapping("/")
    public PriceTrackingDto create(@RequestBody PriceTrackingDto priceTrackingDto) {
        log.debug("ControllerProduct 'Create'");
        return PriceTrackerDtoConverter.convertToDto(servicePriceTracking.create(priceTrackingDto));
    }

    @Override
    @GetMapping("/{id}")
    public PriceTrackingDto read(@PathVariable Long id) {
        log.debug("ControllerProduct 'Read'");
        return PriceTrackerDtoConverter.convertToDto(servicePriceTracking.read(id));
    }

    @Override
    @PutMapping("/{id}")
    public PriceTrackingDto update(@PathVariable Long id, @RequestBody PriceTrackingDto priceTrackingDto) {
        log.debug("ControllerProduct 'Update'");
        return PriceTrackerDtoConverter.convertToDto(servicePriceTracking.update(id, priceTrackingDto));
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.debug("ControllerProduct 'Delete'");
        servicePriceTracking.delete(id);
    }

}
