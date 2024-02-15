package pl.senla.pricer.controller.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.senla.pricer.dto.CategoryDto;
import pl.senla.pricer.entity.Category;
import pl.senla.pricer.exception.CategoryNotFoundException;
import pl.senla.pricer.exception.CategoryNotCreatedException;
import pl.senla.pricer.service.ServiceCategory;
import pl.senla.pricer.utils.CategoryDtoConverter;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/v2/categories")
public class ControllerCategoryResponseImpl implements ControllerCategoryResponse {

    @Autowired
    private ServiceCategory serviceCategory;

    @Override
    @GetMapping
    @PreAuthorize("hasAuthority('read')")
    public ResponseEntity<String> readAll(@RequestParam Map<String,String> requestParams) {
        log.debug("ControllerCategory 'ReadAll'");
        try {
            List<CategoryDto> list = serviceCategory.readAll(requestParams).stream()
                    .map(CategoryDtoConverter::convertCategoryToDto)
                    .toList();
            if (list.isEmpty()) {
                return new ResponseEntity<>("list is empty", HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(list.toString(), HttpStatus.OK);
        } catch (RuntimeException e) {
            log.warn(e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @PostMapping("/")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<String> create(@RequestBody CategoryDto categoryDto) {
        log.debug("ControllerCategory 'Create'");
        try {
            Category category = serviceCategory.create(categoryDto);
            if (category != null) {
                CategoryDto categoryDtoNew = CategoryDtoConverter
                        .convertCategoryToDto(category);
                return new ResponseEntity<>(categoryDtoNew.toString(), HttpStatus.CREATED);
            }
            return new ResponseEntity<>(new CategoryNotCreatedException().toString(), HttpStatus.OK);
        } catch (RuntimeException e) {
            log.warn(e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('read')")
    public ResponseEntity<String> read(@PathVariable Long id) {
        log.debug("ControllerCategory 'Read'");
        try {
            Category category = serviceCategory.read(id);
            if (category != null) {
                CategoryDto categoryDto = CategoryDtoConverter.convertCategoryToDto(category);
                return new ResponseEntity<>(categoryDto.toString(), HttpStatus.OK);
            }
            return new ResponseEntity<>(new CategoryNotFoundException(id).toString(), HttpStatus.OK);
        } catch (RuntimeException e) {
            log.warn(e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('update')")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody CategoryDto category) {
        log.debug("ControllerCategory 'Update'");
        try {
            Category categoryUpdate = serviceCategory.update(id, category);
            if (categoryUpdate != null) {
                CategoryDto categoryDtoUpdate = CategoryDtoConverter
                        .convertCategoryToDto(categoryUpdate);
                return new ResponseEntity<>(categoryDtoUpdate.toString(), HttpStatus.OK);
            }
            return new ResponseEntity<>(new CategoryNotCreatedException().toString(), HttpStatus.OK);
        } catch (RuntimeException e) {
            log.warn(e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('delete')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        log.debug("ControllerCategory 'Delete'");
        try {
            if (serviceCategory.read(id) != null) {
                serviceCategory.delete(id);
                return new ResponseEntity<>("Category id " + id + " was deleted.", HttpStatus.OK);
            }
            return new ResponseEntity<>(new CategoryNotFoundException(id).toString(), HttpStatus.OK);
        } catch (RuntimeException e) {
            log.warn(e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
