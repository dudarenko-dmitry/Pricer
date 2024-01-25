package pl.senla.pricer.controller.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.senla.pricer.dto.CategoryDto;
import pl.senla.pricer.exception.CategoryByIdNotFoundException;
import pl.senla.pricer.exception.CategoryNotCreatedException;
import pl.senla.pricer.service.ServiceCategory;
import pl.senla.pricer.utils.CategoryDtoConverter;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("v2/categories")
@Slf4j
public class ControllerCategoryResponseImpl implements ControllerCategoryResponse {

    @Autowired
    private ServiceCategory serviceCategory;

    @Override
    @GetMapping
    public ResponseEntity<String> readAll(@RequestParam Map<String,String> requestParams) {
        log.debug("ControllerCategory 'ReadAll'");
        try {
            List<CategoryDto> list = serviceCategory.readAll(requestParams).stream()
                    .map(CategoryDtoConverter::convertCategoryToDto)
                    .toList();
            return new ResponseEntity<>(list.toString(), HttpStatus.OK);
        } catch (CategoryByIdNotFoundException e) {
            return new ResponseEntity<>("Could not read Categories' list", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @PostMapping("/")
    public ResponseEntity<String> create(@RequestBody CategoryDto categoryDto) {
        log.debug("ControllerCategory 'Create'");
        try {
            CategoryDto category = CategoryDtoConverter.convertCategoryToDto(serviceCategory.create(categoryDto));
            return new ResponseEntity<>(category.toString(), HttpStatus.CREATED);
        } catch (CategoryNotCreatedException e) {
            log.debug(e.toString());
            return new ResponseEntity<>("Category not created", HttpStatus.FORBIDDEN);
        }
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<String> read(@PathVariable Long id) {
        log.debug("ControllerCategory 'Read'");
        try {
            CategoryDto categoryDto = CategoryDtoConverter.convertCategoryToDto(serviceCategory.read(id));
            return new ResponseEntity<>(categoryDto.toString(), HttpStatus.OK);
        } catch (CategoryByIdNotFoundException e) {
            return new ResponseEntity<>("Category not found: " + e, HttpStatus.NO_CONTENT);
        }
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        log.debug("ControllerCategory 'Update'");
        try {
            CategoryDto categoryDtoUpdate = CategoryDtoConverter.convertCategoryToDto(serviceCategory.update(id, categoryDto));
            return new ResponseEntity<>(categoryDtoUpdate.toString(), HttpStatus.OK);
        } catch (CategoryByIdNotFoundException | CategoryNotCreatedException e) {
            return new ResponseEntity<>("Category not updated: " + e, HttpStatus.NO_CONTENT);
        }
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        log.debug("ControllerCategory 'Delete'");
        try {
            serviceCategory.delete(id);
            return new ResponseEntity<>("Category was deleted.", HttpStatus.OK);
        } catch (CategoryByIdNotFoundException e) {
            return new ResponseEntity<>("Category not found", HttpStatus.NO_CONTENT);
        }
    }
}
