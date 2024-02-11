package pl.senla.pricer.utils;

import org.springframework.stereotype.Component;
import pl.senla.pricer.dto.PriceTrackingDto;

@Component
public final class PriceTrackingDtoConverterString {

    private PriceTrackingDtoConverterString(){}

    public static PriceTrackingDto convertStringToPriceTrackingDto(String productString) {
        String[] text = productString.split(";");
        return PriceTrackingDto.builder()
                .productName(text[0])
                .address(text[1])
                .price(Integer.valueOf(text[2]))
                .dateString(text[3])
                .build();
    }
}
