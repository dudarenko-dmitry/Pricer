package pl.senla.pricer.utils;

import org.springframework.stereotype.Component;
import pl.senla.pricer.dto.ShopDto;
import pl.senla.pricer.entity.Shop;

@Component
public final class ShopDtoConverter {

    private ShopDtoConverter() {}

    public static ShopDto convertShopToDto(Shop shop) {
        return ShopDto.builder()
                .name(shop.getName())
                .city(shop.getCity())
                .address(shop.getAddress())
                .build();
    }

    public static Shop convertDtoToShop(ShopDto shopDto) {
        return Shop.builder()
                .name(shopDto.getName())
                .city(shopDto.getCity())
                .address(shopDto.getAddress())
                .build();
    }

}
