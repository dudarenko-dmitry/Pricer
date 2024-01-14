package pl.senla.pricer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.senla.pricer.entity.Product;
import pl.senla.pricer.entity.Shop;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceTrackingDto {

    private Product product;
    private Shop shop;
    private Integer price;
    private LocalDate date;

}
