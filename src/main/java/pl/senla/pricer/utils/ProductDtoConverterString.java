package pl.senla.pricer.utils;

import org.springframework.stereotype.Component;
import pl.senla.pricer.dto.ProductDto;

@Component
public final class ProductDtoConverterString {

    private ProductDtoConverterString(){}

    public static ProductDto convertStringToProductDto(String productString) {
        String[] text = productString.split(";");
        return ProductDto.builder()
                .name(text[0])
                .categoryName(text[1])
                .build();
    }

}
