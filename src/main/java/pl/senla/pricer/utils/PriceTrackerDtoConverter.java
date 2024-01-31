package pl.senla.pricer.utils;

import pl.senla.pricer.dto.PriceTrackingDto;
import pl.senla.pricer.entity.PriceTracking;

import java.time.LocalDate;

public final class PriceTrackerDtoConverter {

    private PriceTrackerDtoConverter() {
    }


    public static PriceTrackingDto convertToDto(PriceTracking priceTracking) {
        return PriceTrackingDto.builder()
                .productName(priceTracking.getProduct().getName())
                .address(priceTracking.getShop().getAddress())
                .price(priceTracking.getPrice())
                .dateString(convertDateToString(priceTracking.getDate()))
                .build();
    }

    private static String convertDateToString(LocalDate date) {
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        return year + "-" + month + "-" + day;
    }
}
