package pl.senla.pricer.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.senla.pricer.dao.DaoCategory;
import pl.senla.pricer.dto.CategoryDto;
import pl.senla.pricer.entity.Category;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceCategoryImplTest {
    @InjectMocks
    private ServiceCategoryImpl serviceCategoryMock;
    @Mock
    private DaoCategory daoCategoryMock;

    @Test
    void readAll_emptyParameters_success() {
        Map<String, String> requestParams = new HashMap<>();
        List<Category> savedList = new ArrayList<>();
        savedList.add(new Category(1L, "Category1"));
        savedList.add(new Category(2L, "Category2"));
        savedList.add(new Category(3L, "Category3"));

        when(daoCategoryMock.findAll()).thenReturn(savedList);

        List<Category> readList = serviceCategoryMock.readAll(requestParams);

        assertNotNull(readList);
        assertTrue(requestParams.isEmpty());
        assertEquals(savedList, readList);
        verify(daoCategoryMock, times(1)).findAll();
    }

    @Test
    void readAll_orderByName_success() {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("sort", "name");
        Category category1 = new Category(1L, "Category C");
        Category category2 = new Category(2L, "Category A");
        Category category3 = new Category(3L, "Category B");
        List<Category> sortedList = new ArrayList<>();
        sortedList.add(0, category2);
        sortedList.add(1, category3);
        sortedList.add(2, category1);

        when(daoCategoryMock.findAllByOrderByName()).thenReturn(sortedList);

        List<Category> readList = serviceCategoryMock.readAll(requestParams);

        assertNotNull(readList);
        assertEquals(sortedList.get(0), readList.get(0));
        assertEquals(sortedList.get(1), readList.get(1));
        assertEquals(sortedList.get(2), readList.get(2));
        verify(daoCategoryMock, times(1)).findAllByOrderByName();
    }

    @Test
    void readAll_findByName_success() {
        Map<String, String> requestParams = new HashMap<>();
        String parameter = "name";
        String name = "Category A";
        requestParams.put(parameter, name);
        Category category1 = new Category(1L, "Category C");
        Category category2 = new Category(2L, "Category A");
        Category category3 = new Category(3L, "Category B");
        List<Category> savedList = new ArrayList<>();
        savedList.add(category2);

        when(daoCategoryMock.findByName(name)).thenReturn(category2);

        List<Category> readList = serviceCategoryMock.readAll(requestParams);

        assertNotNull(readList);
        assertNotNull(requestParams);
        assertEquals(requestParams.get(parameter), name);
        assertEquals(savedList, readList);
        verify(daoCategoryMock, times(1)).findByName(name);
    }

    @Test
    void readAll_throwsException() {
        Map<String, String> requestParams = new HashMap<>();
        when(daoCategoryMock.findAll()).thenReturn(null);
        assertThrows(NullPointerException.class, () -> serviceCategoryMock.readAll(requestParams));
    }

    @Test
    public void createNewCategory_success() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("TestCategory");
        Category savedCategory = new Category();
        savedCategory.setName("TestCategory");

        when(daoCategoryMock.findByName("TestCategory")).thenReturn(null);
        when(daoCategoryMock.save(any(Category.class))).thenReturn(savedCategory);

        Category createdCategory = serviceCategoryMock.create(categoryDto);

        assertNotNull(createdCategory);
        assertEquals("TestCategory", createdCategory.getName());
        assertEquals(savedCategory, createdCategory);
        verify(daoCategoryMock, times(1)).findByName("TestCategory");
        verify(daoCategoryMock, times(1)).save(any(Category.class));
    }

    @Test
    void createNewCategory_forbidden() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("TestCategory");

        Category savedCategory = new Category();
        savedCategory.setName("TestCategory");

        when(daoCategoryMock.findByName("TestCategory")).thenReturn(any(Category.class));

        Category createdCategory = serviceCategoryMock.create(categoryDto);

        assertNull(createdCategory);
        verify(daoCategoryMock, times(1)).findByName("TestCategory");
        verify(daoCategoryMock, times(0)).save(any(Category.class));
    }

    @Test
    void read_success() {
        Category savedCategory = new Category(1L, "TestCategory");
        Long idTest = 1L;

        when(daoCategoryMock.findById(idTest)).thenReturn(Optional.of(savedCategory));

        Category readCategory = serviceCategoryMock.read(idTest);

        assertNotNull(readCategory);
        assertEquals(savedCategory, readCategory);
        verify(daoCategoryMock, times(1)).findById(idTest);
    }

    @Test
    void read_throwsException() {
        Long idTest = 2L;

        when(daoCategoryMock.findById(idTest)).thenReturn(null);
        assertThrows(NullPointerException.class, () -> serviceCategoryMock.read(idTest));
        verify(daoCategoryMock, times(1)).findById(idTest);
    }

    @Test
    void update_success() {
        Category categoryOld = new Category(1L, "CategoryOld");
        CategoryDto categoryDtoNew = new CategoryDto("CategoryNew");
        Category categoryNew = new Category(1L, "CategoryNew");
        Long idTest = 1L;

        when(daoCategoryMock.findById(idTest)).thenReturn(Optional.of(categoryOld));
        when(daoCategoryMock.save(any(Category.class))).thenReturn(categoryNew);

        Category categoryUpdated = serviceCategoryMock.update(idTest, categoryDtoNew);

        assertNotNull(categoryUpdated);
        assertEquals(categoryNew, categoryUpdated);
        verify(daoCategoryMock, times(1)).findById(idTest);
        verify(daoCategoryMock, times(1)).save(any(Category.class));
    }

    @Test
    void update_throwsException() {
        CategoryDto categoryDtoNew = new CategoryDto("CategoryNew");
        Long idTest = 1L;

        when(daoCategoryMock.findById(idTest)).thenReturn(null);

        assertThrows(NullPointerException.class, () -> serviceCategoryMock.update(idTest, categoryDtoNew));
        verify(daoCategoryMock, times(1)).findById(idTest);
    }

    @Test
    void delete_success() {
        Long idTest = 1L;

        when(daoCategoryMock.existsById(idTest)).thenReturn(true);
        serviceCategoryMock.delete(idTest);

        verify(daoCategoryMock, times(1)).deleteById(idTest);
    }

    @Test
    void delete_throwsException() {
        Long idTest = 1L;
        when(daoCategoryMock.existsById(idTest)).thenReturn(false);
        assertThrows(NullPointerException.class, () -> serviceCategoryMock.delete(idTest));
        verify(daoCategoryMock, times(1)).existsById(idTest);
    }

}