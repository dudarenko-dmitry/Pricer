package pl.senla.pricer.controller.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.senla.pricer.dto.CategoryDto;
import pl.senla.pricer.service.ServiceCategory;
import pl.senla.pricer.utils.CategoryDtoConverter;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/categories")
@Slf4j
public class ControllerCategoryRest implements ControllerCategory {

    @Autowired
    private ServiceCategory serviceCategory;

    @Override
    @GetMapping
    public List<CategoryDto> readAll(@RequestParam Map<String,String> requestParams) {
        log.debug("ControllerCategory 'ReadAll'");
        if(requestParams.isEmpty()) {
            return serviceCategory.readAll(requestParams).stream()
                    .map(CategoryDtoConverter::convertCategoryToDto)
                    .toList();
        }
        String sort = requestParams.get("sort");
        String name = requestParams.get("name");
        log.info("Categories sorted by {} or filtered by name {}", sort, name);
        if (name != null) {
            return Collections.singletonList(CategoryDtoConverter.convertCategoryToDto(serviceCategory.readByName(name)));
        } else {
            switch(sort) {
                case "id" -> {
                    return serviceCategory.readAll(requestParams).stream()
                            .map(CategoryDtoConverter::convertCategoryToDto)
                            .toList();
                }
                case "name" ->{
                    return serviceCategory.readAllOrderByName().stream()
                            .map(CategoryDtoConverter::convertCategoryToDto)
                            .toList();}
                default -> {
                    log.warn("Please check your input.");
                    return null;
                }
            }
        }
    }

    @Override
    @PostMapping("/")
    public CategoryDto create(@RequestBody CategoryDto categoryDto) {
        log.debug("ControllerCategory 'Create'");
        return CategoryDtoConverter.convertCategoryToDto(serviceCategory.create(categoryDto));
    }

    @Override
    @GetMapping("/{id}")
    public CategoryDto read(@PathVariable Long id) {
        log.debug("ControllerCategory 'Read'");
        return CategoryDtoConverter.convertCategoryToDto(serviceCategory.read(id));
    }

    @Override
    @PutMapping("/{id}")
    public CategoryDto update(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        log.debug("ControllerCategory 'Update'");
        return CategoryDtoConverter.convertCategoryToDto(serviceCategory.update(id, categoryDto));
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.debug("ControllerCategory 'Delete'");
        serviceCategory.delete(id);
    }
}
