package pl.senla.pricer.loader;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.senla.pricer.dto.PriceTrackingDto;
import pl.senla.pricer.utils.PriceTrackingDtoConverterString;

@Component
@Data
@Slf4j
public class ConverterPriceTrackingDto implements ConverterEntity<PriceTrackingDto> {

    @Override
    public PriceTrackingDto convertStringToEntity(String csvPriceTracking) {
        return PriceTrackingDtoConverterString.convertStringToPriceTrackingDto(csvPriceTracking);
    }
}
