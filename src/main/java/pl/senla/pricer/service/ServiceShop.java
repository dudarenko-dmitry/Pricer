package pl.senla.pricer.service;

import pl.senla.pricer.dto.ShopDto;
import pl.senla.pricer.entity.Shop;

import java.util.List;

public interface ServiceShop extends ServiceCRUDAll<Shop, ShopDto> {

    Shop readByAddress(String address);
//    List<Shop> readAllSortByName();
//    List<Shop> readAllByName(String name);

}
