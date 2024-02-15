package pl.senla.pricer.loader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import pl.senla.pricer.dto.PriceTrackingDto;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class PriceTrackingLoader implements DataLoader<PriceTrackingDto> {

    @Autowired
    @Qualifier("converterPriceTrackingDto")
    private ConverterEntity<PriceTrackingDto> converter;

    @Override
    public List<PriceTrackingDto> loadData(String filePath) {
        log.debug("PriceTrackingLoader: start loading file");
        EntityReader<PriceTrackingDto> priceTrackingDtoReader = new EntityReader<>();
        try {
            return priceTrackingDtoReader.load(filePath, converter);
        } catch (IOException e) {
            log.error("PriceTracking's list couldn't be loaded");
            throw new RuntimeException(e);
        }
    }
}