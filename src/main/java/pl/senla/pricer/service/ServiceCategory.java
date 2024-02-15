package pl.senla.pricer.service;

import pl.senla.pricer.dto.CategoryDto;
import pl.senla.pricer.entity.Category;

import java.util.List;

public interface ServiceCategory extends ServiceCRUDAll<Category, CategoryDto> {

//    List<Category> readAllOrderByName();
    Category readByName(String filter);

}
