package pl.senla.pricer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceTrackingDto {

    private String productName;
    private String address;
    private Integer price;
    private String dateString;

}
