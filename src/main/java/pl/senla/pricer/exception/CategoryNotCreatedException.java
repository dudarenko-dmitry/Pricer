package pl.senla.pricer.exception;

import pl.senla.pricer.dto.CategoryDto;

public class CategoryNotCreatedException extends RuntimeException {

    public CategoryNotCreatedException(CategoryDto categoryDto) {
        super("Could not create Category: " + categoryDto.toString());
    }

}
