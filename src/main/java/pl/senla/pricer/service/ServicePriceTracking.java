package pl.senla.pricer.service;

import pl.senla.pricer.dto.PriceTrackingDto;
import pl.senla.pricer.entity.PriceTracking;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ServicePriceTracking extends ServiceCRUDAll<PriceTracking, PriceTrackingDto> {

    List<PriceTracking> createFromFile(String filePath);

    Map<LocalDate, Integer> getPriceDynamic(Map<String, String> requestParams);

}
