package pl.senla.pricer.service;

import pl.senla.pricer.dto.ProductDto;
import pl.senla.pricer.entity.Product;

import java.util.List;

public interface ServiceProduct extends ServiceCRUDAll<Product, ProductDto> {

//    List<Product> readAllByOrderByName();
//    List<Product> readAllByOrderByCategory();
    List<Product> createFromFile(String filePath);
    Product readByName(String name);

}
