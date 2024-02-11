package pl.senla.pricer.loader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import pl.senla.pricer.dto.ProductDto;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class ProductLoader implements DataLoader<ProductDto> {

    @Autowired
    @Qualifier("converterProductDto")
    private ConverterEntity<ProductDto> converter;

    @Override
    public List<ProductDto> loadData(String filePath) {
        log.debug("ProductLoader: start loading file");
        try {
            EntityReader<ProductDto> productDtoReader = new EntityReader<>();
            return productDtoReader.load(filePath, converter);
        } catch (IOException e) {
            log.error("Product's list couldn't be loaded");
            throw new RuntimeException(e);
        }
    }
}