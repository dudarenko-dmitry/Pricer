package pl.senla.pricer.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.senla.pricer.dao.DaoShop;
import pl.senla.pricer.dto.ShopDto;
import pl.senla.pricer.entity.Shop;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceShopImplTest {

    @InjectMocks
    private ServiceShopImpl serviceShopMock;
    @Mock
    private DaoShop daoShopMock;

    @Test
    void readAll_emptyParameters_success() {
        Map<String, String> requestParams = new HashMap<>();
        List<Shop> savedList = new ArrayList<>();
        savedList.add(new Shop(1L, "Name 2", "City 1", "Address 3"));
        savedList.add(new Shop(2L, "Name 3", "City 2", "Address 1"));
        savedList.add(new Shop(3L, "Name 1", "City 3", "Address 2"));

        when(daoShopMock.findAll()).thenReturn(savedList);

        List<Shop> readList = serviceShopMock.readAll(requestParams);

        assertNotNull(savedList);
        assertNotNull(readList);
        assertTrue(requestParams.isEmpty());
        assertEquals(savedList, readList);
        verify(daoShopMock, times(1)).findAll();
    }

    @Test
    void readAll_orderByName_success() {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("sort", "name");
        Shop shop1 = new Shop(1L, "Name 2", "City 1", "Address 3");
        Shop shop2 = new Shop(2L, "Name 3", "City 2", "Address 1");
        Shop shop3 = new Shop(3L, "Name 1", "City 3", "Address 2");
        List<Shop> sortedList = new ArrayList<>();
        sortedList.add(0, shop3);
        sortedList.add(1, shop1);
        sortedList.add(2, shop2);

        when(daoShopMock.findAllByOrderByName()).thenReturn(sortedList);

        List<Shop> readList = serviceShopMock.readAll(requestParams);

        assertNotNull(readList);
        assertEquals(sortedList, readList);
        assertEquals(sortedList.get(0), readList.get(0));
        assertEquals(sortedList.get(1), readList.get(1));
        assertEquals(sortedList.get(2), readList.get(2));
        verify(daoShopMock, times(1)).findAllByOrderByName();
    }

    @Test
    void readAll_orderByAddress_success() {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("sort", "address");
        Shop shop1 = new Shop(1L, "Name 2", "City 1", "Address 3");
        Shop shop2 = new Shop(2L, "Name 3", "City 2", "Address 1");
        Shop shop3 = new Shop(3L, "Name 1", "City 3", "Address 2");
        List<Shop> sortedList = new ArrayList<>();
        sortedList.add(0, shop2);
        sortedList.add(1, shop3);
        sortedList.add(2, shop1);

        when(daoShopMock.findAllByOrderByAddress()).thenReturn(sortedList);

        List<Shop> readList = serviceShopMock.readAll(requestParams);

        assertNotNull(readList);
        assertEquals(sortedList, readList);
        assertEquals(sortedList.get(0), readList.get(0));
        assertEquals(sortedList.get(1), readList.get(1));
        assertEquals(sortedList.get(2), readList.get(2));
        verify(daoShopMock, times(1)).findAllByOrderByAddress();
    }

    @Test
    void readAll_byName_success() {
        Map<String, String> requestParams = new HashMap<>();
        String parameter = "name";
        String testName = "Name 2";
        requestParams.put(parameter, testName);
        Shop shop1 = new Shop(1L, "Name 2", "City 1", "Address 3");
        Shop shop2 = new Shop(2L, "Name 3", "City 2", "Address 1");
        Shop shop3 = new Shop(3L, "Name 2", "City 3", "Address 2");
        List<Shop> filteredList = new ArrayList<>();
        filteredList.add(0, shop3);
        filteredList.add(1, shop1);

        when(daoShopMock.findAllByName(testName)).thenReturn(filteredList);

        List<Shop> readList = serviceShopMock.readAll(requestParams);

        assertNotNull(readList);
        assertEquals(filteredList, readList);
        verify(daoShopMock, times(1)).findAllByName(testName);
    }

    @Test
    void readAll_findByAddress_success() {
        Map<String, String> requestParams = new HashMap<>();
        String parameter = "address";
        String testAddress = "Address 1";
        requestParams.put(parameter, testAddress);
        Shop shop1 = new Shop(1L, "Name 2", "City 1", "Address 3");
        Shop shop2 = new Shop(2L, "Name 3", "City 2", "Address 1");
        Shop shop3 = new Shop(3L, "Name 2", "City 3", "Address 2");

        when(daoShopMock.findByAddress(testAddress)).thenReturn(shop2);

        List<Shop> readList = serviceShopMock.readAll(requestParams);

        assertNotNull(readList);
        assertEquals(shop2, readList.get(0));
        verify(daoShopMock, times(1)).findByAddress(testAddress);
    }

    @Test
    void readAll_throwsException() {
        Map<String, String> requestParams = new HashMap<>();
        when(daoShopMock.findAll()).thenReturn(null);
        assertThrows(NullPointerException.class, () -> serviceShopMock.readAll(requestParams));
    }

    @Test
    void create_success() {
        Shop testShop = new Shop(1L, "Name Test", "City Test", "Address Test");
        ShopDto testDtoShop = new ShopDto("Name Test", "City Test", "Address Test");

        when(daoShopMock.findByAddress("Address Test")).thenReturn(null);
        when(daoShopMock.save(any(Shop.class))).thenReturn(testShop);

        Shop createdShop = serviceShopMock.create(testDtoShop);

        assertNotNull(createdShop);
        assertEquals("Address Test", createdShop.getAddress());
        assertEquals(testShop, createdShop);
        verify(daoShopMock, times(1)).findByAddress("Address Test");
        verify(daoShopMock, times(1)).save(any(Shop.class));
    }

    @Test
    void create_forbidden() {
        ShopDto testDtoShop = new ShopDto("Name Test", "City Test", "Address Test");
        String testAddress = "Address Test";

        when(daoShopMock.findByAddress(testAddress)).thenReturn(any(Shop.class));

        Shop createdShop = serviceShopMock.create(testDtoShop);

        assertNull(createdShop);
        verify(daoShopMock, times(1)).findByAddress(testAddress);
        verify(daoShopMock, times(1)).save(any(Shop.class));
    }

    @Test
    void read_success() {
        Shop testShop = new Shop(1L, "Name Test", "City Test", "Address Test");
        Long testId = 1L;

        when(daoShopMock.findById(testId)).thenReturn(Optional.of(testShop));

        Shop readShop = serviceShopMock.read(testId);

        assertNotNull(readShop);
        assertEquals(testShop, readShop);
        verify(daoShopMock, times(1)).findById(testId);
    }

    @Test
    void read_throwsException() {
        Long testId = 1L;
        assertThrows(NullPointerException.class, () -> serviceShopMock.read(testId));
        verify(daoShopMock, times(1)).findById(testId);
    }

    @Test
    void readByAddress_success() {
        Shop testShop = new Shop(1L, "Name 2", "City 1", "Address test");
        String testAddress = "Address test";

        when(daoShopMock.findByAddress(testAddress)).thenReturn(testShop);

        Shop readShop = serviceShopMock.readByAddress(testAddress);

        assertNotNull(testShop);
        assertEquals(testShop, readShop);
        verify(daoShopMock, times(1)).findByAddress(testAddress);
    }

    @Test
    void update_success() {
        Shop shopOld = new Shop(1L, "Name Test", "City Test", "Address Test");
        Shop shopNew = new Shop(1L, "Name New", "City New", "Address New");
        Long testId =  1L;
        ShopDto shopDtoNew = new ShopDto("Name New", "City New", "Address New");

        when(daoShopMock.findById(testId)).thenReturn(Optional.of(shopOld));
        when(daoShopMock.save(any(Shop.class))).thenReturn(shopNew);

        Shop shopUpdated = serviceShopMock.update(testId, shopDtoNew);

        assertNotNull(shopUpdated);
        assertEquals(shopUpdated, shopNew);
        verify(daoShopMock, times(1)).findById(testId);
        verify(daoShopMock, times(1)).save(any(Shop.class));
    }

    @Test
    void update_throwsException() {
        Long testId =  2L;
        ShopDto shopDtoNew = new ShopDto("Name New", "City New", "Address New");

        assertThrows(NullPointerException.class, () -> serviceShopMock.update(testId, shopDtoNew));
        verify(daoShopMock, times(1)).findById(testId);
    }

    @Test
    void delete_success() {
        Long testId =  1L;
        when(daoShopMock.existsById(testId)).thenReturn(true);
        serviceShopMock.delete(testId);
        verify(daoShopMock, times(1)).existsById(testId);
        verify(daoShopMock, times(1)).deleteById(testId);
    }


    @Test
    void delete_throwsException() {
        Long testId =  1L;
        assertThrows(NullPointerException.class, () -> serviceShopMock.delete(testId));
        verify(daoShopMock, times(1)).existsById(testId);
    }
}