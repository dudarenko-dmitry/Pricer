package pl.senla.pricer.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.senla.pricer.dto.CategoryDto;
import pl.senla.pricer.entity.Category;

@Component
@Slf4j
public final class CategoryDtoConverter {

    private CategoryDtoConverter(){}

    public static CategoryDto convertCategoryToDto(Category category) {
        return new CategoryDto(category.getName());
    }

}
