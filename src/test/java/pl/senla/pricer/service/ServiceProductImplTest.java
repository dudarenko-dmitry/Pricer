package pl.senla.pricer.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.senla.pricer.dao.DaoProduct;
import pl.senla.pricer.dto.ProductDto;
import pl.senla.pricer.entity.Category;
import pl.senla.pricer.entity.Product;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceProductImplTest {

    @InjectMocks
    private ServiceProductImpl serviceProductMock;
    @Mock
    private DaoProduct daoProductMock;

    private static Category testCategory;
    private static String testCategoryName;

    @BeforeAll
    static void initTestCategory() {
        testCategoryName = "test Category";
        testCategory = new Category(1L, testCategoryName);
    }

    @Test
    void readAll_emptyParameters_success() {
        Map<String, String> requestParams = new HashMap<>();
        List<Product> savedList = new ArrayList<>();
        savedList.add(new Product(1L, "Name 2", testCategory));
        savedList.add(new Product(2L, "Name 3", testCategory));
        savedList.add(new Product(3L, "Name 1", testCategory));

        when(daoProductMock.findAll()).thenReturn(savedList);

        List<Product> readList = serviceProductMock.readAll(requestParams);

        assertNotNull(readList);
        assertTrue(requestParams.isEmpty());
        assertEquals(savedList, readList);
        verify(daoProductMock, times(1)).findAll();
    }

    @Test
    void readAll_findByCategoryName_success() {
        Map<String, String> requestParams = new HashMap<>();
        String parameter = "categoryName";
        requestParams.put(parameter, testCategoryName);
        String testCategoryName2 = "test Category 2";
        Category testCategory2 = new Category(2L, testCategoryName2);

        Product product1 = new Product(1L, "Name 1", testCategory);
        Product product2 = new Product(2L, "Name 2", testCategory2);
        Product product3 = new Product(3L, "Name 3", testCategory);
        Product product4 = new Product(4L, "Name 4", testCategory2);
        List<Product> listOfCategorySaved = new ArrayList<>();
        listOfCategorySaved.add(product1);
        listOfCategorySaved.add(product3);

        when(daoProductMock.findAllByCategory(testCategoryName)).thenReturn(listOfCategorySaved);

        List<Product> readList = serviceProductMock.readAll(requestParams);

        assertNotNull(readList);
        assertEquals(readList, listOfCategorySaved);
        verify(daoProductMock, times(1)).findAllByCategory(testCategoryName);
    }

    @Test
    void readAll_findAllByCategoryNameByOrderByName_success() {
        Map<String, String> requestParams = new HashMap<>();
        String parameter1 = "sort";
        String value1 = "name";
        requestParams.put(parameter1, value1);
        String parameter2 = "categoryName";
        requestParams.put(parameter2, testCategoryName);

        Product product1 = new Product(1L, "Name 3", testCategory);
        Product product3 = new Product(3L, "Name 1", testCategory);
        List<Product> listOfCategorySaved = new ArrayList<>();
        listOfCategorySaved.add(0, product3);
        listOfCategorySaved.add(1, product1);

        when(daoProductMock.findAllByCategoryByOrderByName(testCategoryName)).thenReturn(listOfCategorySaved);

        List<Product> readList = serviceProductMock.readAll(requestParams);

        assertNotNull(readList);
        assertEquals(readList, listOfCategorySaved);
        verify(daoProductMock, times(1)).findAllByCategoryByOrderByName(testCategoryName);
    }

    @Test
    void readAll_findAllByOrderByCategoryName_success() {
        Map<String, String> requestParams = new HashMap<>();
        String parameter1 = "sort";
        String value1 = "categoryName";
        requestParams.put(parameter1, value1);

        String testCategoryName2 = "test Category 2";
        Category testCategory2 = new Category(2L, testCategoryName2);
        Product product1 = new Product(1L, "Name 1", testCategory);
        Product product2 = new Product(2L, "Name 2", testCategory2);
        Product product3 = new Product(3L, "Name 3", testCategory);
        Product product4 = new Product(4L, "Name 4", testCategory2);
        List<Product> listOfCategorySaved = new ArrayList<>();
        listOfCategorySaved.add(0, product3);
        listOfCategorySaved.add(1, product1);
        listOfCategorySaved.add(2, product2);
        listOfCategorySaved.add(3, product4);

        when(daoProductMock.findAllByOrderByCategoryName()).thenReturn(listOfCategorySaved);

        List<Product> readList = serviceProductMock.readAll(requestParams);

        assertNotNull(readList);
        assertEquals(readList, listOfCategorySaved);
        verify(daoProductMock, times(1)).findAllByOrderByCategoryName();
    }


    @Test
    void readAll_findAllByOrderByName_success() {
        Map<String, String> requestParams = new HashMap<>();
        String parameter1 = "sort";
        String value1 = "name";
        requestParams.put(parameter1, value1);

        String testCategoryName2 = "test Category 2";
        Category testCategory2 = new Category(2L, testCategoryName2);
        Product product1 = new Product(1L, "Name 3", testCategory);
        Product product2 = new Product(2L, "Name 2", testCategory2);
        Product product3 = new Product(3L, "Name 1", testCategory);
        Product product4 = new Product(4L, "Name 4", testCategory2);
        List<Product> listOfCategorySaved = new ArrayList<>();
        listOfCategorySaved.add(0, product3);
        listOfCategorySaved.add(1, product2);
        listOfCategorySaved.add(2, product1);
        listOfCategorySaved.add(3, product4);

        when(daoProductMock.findAllByOrderByName()).thenReturn(listOfCategorySaved);

        List<Product> readList = serviceProductMock.readAll(requestParams);

        assertNotNull(readList);
        assertEquals(readList, listOfCategorySaved);
        verify(daoProductMock, times(1)).findAllByOrderByName();
    }

    @Test
    void readAll_throwsException() {
        Map<String, String> requestParams = new HashMap<>();
        when(daoProductMock.findAll()).thenReturn(null);
        assertThrows(NullPointerException.class, () -> serviceProductMock.readAll(requestParams));
    }

    @Test
    void create_success() {
        Product testProduct = new Product(1L, "test Name", testCategory);
        ProductDto testDtoProduct = new ProductDto("test Name", testCategoryName);

        when(daoProductMock.findByName("test Name")).thenReturn(null);
        when(daoProductMock.save(any(Product.class))).thenReturn(testProduct);

        Product createdProduct = serviceProductMock.create(testDtoProduct);

        assertNotNull(createdProduct);
        assertEquals("test Name", createdProduct.getName());
        assertEquals(testProduct, createdProduct);
        verify(daoProductMock, times(1)).findByName("test Name");
        verify(daoProductMock, times(1)).save(any(Product.class));
    }

    @Test
    void create_forbidden() {
        String testName = "test Name";
        ProductDto testDtoProduct = new ProductDto(testName, testCategoryName);

        when(daoProductMock.findByName(testName)).thenReturn(any(Product.class));

        Product createdProduct = serviceProductMock.create(testDtoProduct);

        assertNull(createdProduct);
        verify(daoProductMock, times(1)).findByName(testName);
        verify(daoProductMock, times(1)).save(any(Product.class));
    }

    @Test
    void read_success() {
        Product testProduct = new Product(1L, "test Name", testCategory);
        Long testId = 1L;

        when(daoProductMock.findById(testId)).thenReturn(Optional.of(testProduct));

        Product readProduct = serviceProductMock.read(testId);

        assertNotNull(readProduct);
        assertEquals(testProduct, readProduct);
        verify(daoProductMock, times(1)).findById(testId);
    }

    @Test
    void read_throwsException() {
        Long testId = 1L;
        assertThrows(NullPointerException.class, () -> serviceProductMock.read(testId));
        verify(daoProductMock, times(1)).findById(testId);
    }

    @Test
    void readByName_success() {
        String testName = "test Name";
        Product testProduct = new Product(1L, testName, testCategory);

        when(daoProductMock.findByName(testName)).thenReturn(testProduct);

        Product readProduct = serviceProductMock.readByName(testName);

        assertNotNull(testProduct);
        assertEquals(testProduct, readProduct);
        verify(daoProductMock, times(1)).findByName(testName);
    }

    @Test
    void update_success() {
        Product productOld = new Product(1L, "test Name", testCategory);
        Product productNew = new Product(1L, "test Name NEW", testCategory);
        Long testId =  1L;
        ProductDto productDtoNew = new ProductDto("test Name NEW", testCategoryName);

        when(daoProductMock.findById(testId)).thenReturn(Optional.of(productOld));
        when(daoProductMock.save(any(Product.class))).thenReturn(productNew);

        Product productUpdated = serviceProductMock.update(testId, productDtoNew);

        assertNotNull(productUpdated);
        assertEquals(productUpdated, productNew);
        verify(daoProductMock, times(1)).findById(testId);
        verify(daoProductMock, times(1)).save(any(Product.class));
    }

    @Test
    void update_throwsException() {
        Long testId =  1L;
        ProductDto productDtoNew = new ProductDto("test name", testCategoryName);

        assertThrows(NullPointerException.class, () -> serviceProductMock.update(testId, productDtoNew));
        verify(daoProductMock, times(1)).findById(testId);
    }

    @Test
    void delete_success() {
        Long testId =  1L;
        when(daoProductMock.existsById(testId)).thenReturn(true);
        serviceProductMock.delete(testId);
        verify(daoProductMock, times(1)).existsById(testId);
        verify(daoProductMock, times(1)).deleteById(testId);
    }

    @Test
    void delete_throwsException() {
        Long testId =  1L;
        assertThrows(NullPointerException.class, () -> serviceProductMock.delete(testId));
        verify(daoProductMock, times(1)).existsById(testId);
    }
}