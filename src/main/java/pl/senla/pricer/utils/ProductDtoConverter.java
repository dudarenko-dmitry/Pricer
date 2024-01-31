package pl.senla.pricer.utils;

import org.springframework.stereotype.Component;
import pl.senla.pricer.dto.ProductDto;
import pl.senla.pricer.entity.Product;

@Component
public final class ProductDtoConverter {

    private ProductDtoConverter(){}

    public static ProductDto convertProductToDto(Product product) {
        return ProductDto.builder()
                .name(product.getName())
                .categoryName(product.getCategory().getName())
                .build();
    }

}
