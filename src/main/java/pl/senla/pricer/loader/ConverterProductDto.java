package pl.senla.pricer.loader;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.senla.pricer.dto.ProductDto;
import pl.senla.pricer.utils.ProductDtoConverterString;

@Component
@Data
@Slf4j
public class ConverterProductDto implements ConverterEntity<ProductDto> {

    @Override
    public ProductDto convertStringToEntity(String csvProduct) {
        return ProductDtoConverterString.convertStringToProductDto(csvProduct);
    }
}
