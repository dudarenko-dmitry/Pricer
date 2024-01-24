package pl.senla.pricer.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.senla.pricer.dao.DaoCategory;
import pl.senla.pricer.dto.CategoryDto;
import pl.senla.pricer.entity.Category;
import pl.senla.pricer.exception.CategoryByIdNotFoundException;
import pl.senla.pricer.utils.CategoryDtoConverter;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ServiceCategoryImpl implements ServiceCategory{

    @Autowired
    private DaoCategory daoCategory;

    @Override
    public List<Category> readAll(Map<String, String> requestParams) {
        log.debug("Start ServiceCategory 'ReadAll'");
        List<Category> categories = daoCategory.findAll();
        if (categories.isEmpty()) {
            log.debug("List of Categories is empty");
        }

        if (requestParams.isEmpty()) {
            return categories;
        }
        String sort = requestParams.get("sort");
        String name = requestParams.get("name");
        log.info("Categories sorted by {} or filtered by name {}", sort, name);
        if (name != null) {
            return Collections.singletonList(daoCategory.findByName(name));
        } else {
            switch (sort) {
                case "id" -> {
                    return categories;
                }
                case "name" -> {
                    return daoCategory.findAllByOrderByName();
                }
                default -> throw new IllegalStateException("Unexpected value: " + sort);
            }
        }
    }

    @Override
    @Transactional
    public Category create(CategoryDto categoryDto) {
        log.debug("Start ServiceCategory 'Create'");
        String name = categoryDto.getName();
        if (daoCategory.findByName(name) == null) {
            return daoCategory.save(CategoryDtoConverter.convertDtoToCategory(categoryDto));
        }
        log.debug("ServiceCategory: Category is already exist");
        return null;
    }

    @Override
    public Category read(Long id) {
        log.debug("Start ServiceCategory 'Read by ID'");
        return daoCategory.findById(id)
                .orElseThrow(() -> new CategoryByIdNotFoundException(id));
    }

    @Override
    @Transactional
    public Category update(Long id, CategoryDto categoryDto) {
        log.debug("Start ServiceCategory 'Update'");
        if (daoCategory.findById(id).isPresent()) {
            Category newCategory = CategoryDtoConverter.convertDtoToCategory(categoryDto);
            newCategory.setId(id);
            log.debug("ServiceCategory Updated Category");
            return daoCategory.save(newCategory);
        }
        log.debug(String.valueOf(new CategoryByIdNotFoundException(id)));
        return null;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.debug("Start ServiceCategory 'Delete by ID'");
        if (!daoCategory.existsById(id)) {
            log.debug(String.valueOf(new CategoryByIdNotFoundException(id)));
        } else {
            log.debug("ServiceCategory deleted Category");
            daoCategory.deleteById(id);
        }
    }

    @Override
    public List<Category> readAllOrderByName() {
        log.debug("Start ServiceCategory 'ReadAll sort by Name'");
        List<Category> categories = daoCategory.findAllByOrderByName();
        if (categories.isEmpty()) {
            log.info("List of Categories is empty");
            return categories;
        }
        log.debug("ServiceCategory 'ReadAll' returns List of Categories");
        return categories;
    }

    @Override
    public Category readByName(String name) {
        log.debug("Start ServiceCategory 'Read by ID'");
        Category category = daoCategory.findByName(name);
        if (category == null) {
            log.info("Category with name {} doesn't exists", name);
        }
        return category;
    }
}
