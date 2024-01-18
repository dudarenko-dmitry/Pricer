package pl.senla.pricer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.senla.pricer.dao.DaoShop;
import pl.senla.pricer.dto.ShopDto;
import pl.senla.pricer.entity.Shop;
import pl.senla.pricer.exception.ShopByIdNotFoundException;
import pl.senla.pricer.utils.ShopDtoConverter;

import java.util.List;

@Service
@Slf4j
public class ServiceShopImpl implements ServiceShop {

    @Autowired
    private DaoShop daoShop;

    @Override
    public List<Shop> readAll() {
        log.debug("Start ServiceShop 'ReadAll'");
        List<Shop> shops = daoShop.findAll();
        if (shops.isEmpty()) {
            log.info("List of Shops is empty");
            return shops;
        }
        log.debug("ServiceShop 'ReadAll' returns List of Shops");
        return shops;
    }

    @Override
    public Shop create(ShopDto shopDto) {
        log.debug("Start ServiceShop 'Create'");
        String address = shopDto.getAddress();
        if (daoShop.findByAddress(address) == null) {
            Shop shopNew = ShopDtoConverter.convertDtoToShop(shopDto);
            return daoShop.save(shopNew);
        }
        log.debug("ServiceShop: Shop is already exist");
        return null;
    }

    @Override
    public Shop read(Long id) {
        log.debug("Start ServiceShop 'Read by ID'");
        return daoShop.findById(id)
                .orElseThrow(() -> new ShopByIdNotFoundException(id));
    }

    @Override
    public Shop readByAddress(String address) {
        log.debug("Start ServiceShop 'Read by Address'");
        Shop shop = daoShop.findByAddress(address);
        if (shop == null) {
            log.info("There is no any shop in this address {}: ", address);
        }
        return shop;
    }

    @Override
    public Shop update(Long id, ShopDto shopDto) {
        log.debug("Start ServiceShop 'Update'");
        if (daoShop.findById(id).isPresent()) {
            Shop shopUpdate = ShopDtoConverter.convertDtoToShop(shopDto);
            shopUpdate.setId(id);
            log.debug("ServiceShop Updated Shop");
            return daoShop.save(shopUpdate);
        }
        throw new ShopByIdNotFoundException(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Start ServiceShop 'Delete by ID'");
        if (!daoShop.existsById(id)) {
            log.debug(String.valueOf(new ShopByIdNotFoundException(id)));
        }
        log.debug("Shop was deleted");
        daoShop.deleteById(id);
    }

    @Override
    public List<Shop> readAllSortByName() {
        log.debug("Start ServiceShop 'readAllSortByName'");
        List<Shop> shops = daoShop.findAllByOrderByName();
        if (shops.isEmpty()) {
            log.debug("List of Shops is empty");
            return shops;
        }
        log.debug("ServiceShop 'ReadAll' returns List of Shops sorted by Name");
        return shops;
    }

    @Override
    public List<Shop> readAllByName(String name) {
        log.debug("Start ServiceShop 'readAllByName'");
        List<Shop> shops = daoShop.findAllByName(name);
        if (shops.isEmpty()) {
            log.info("Shops with name {} not found", name);
            return shops;
        }
        log.debug("ServiceShop 'ReadAll' returns List of Shops filtered by Name");
        return shops;
    }

}
