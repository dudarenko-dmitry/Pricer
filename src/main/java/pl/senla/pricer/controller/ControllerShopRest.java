package pl.senla.pricer.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.senla.pricer.dto.ShopDto;
import pl.senla.pricer.entity.Shop;
import pl.senla.pricer.service.ServiceShop;
import pl.senla.pricer.utils.ShopDtoConverter;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/shops")
@Slf4j
public class ControllerShopRest implements ControllerShop {

    @Autowired
    private ServiceShop serviceShop;

    @Override
    @GetMapping
    public List<ShopDto> readAll(@RequestParam Map<String,String> requestParams) {
        log.debug("ControllerShop 'ReadAll'");
        if(requestParams.isEmpty()) {
            return  serviceShop.readAll().stream()
                    .map(ShopDtoConverter::convertShopToDto)
                    .toList();
        }
        String sort = requestParams.get("sort");
        String name = requestParams.get("name");
        String address = requestParams.get("address");
        log.info("Shops sorted by {} or filtered by name {} / address {}", sort, name, address);
        if(name != null) {
            List<Shop> shopsByName = serviceShop.readAllByName(name);
            if(address != null) {
                return shopsByName.stream()
                        .filter(s -> s.getAddress().equals(address))
                        .map(ShopDtoConverter::convertShopToDto)
                        .toList();
            }
            return shopsByName.stream()
                    .map(ShopDtoConverter::convertShopToDto)
                    .toList();
        } else {
            if(address != null) {
                return Collections.singletonList(ShopDtoConverter.convertShopToDto(serviceShop.readByAddress(address)));
            }
            switch (sort) {
                case "id" -> {return serviceShop.readAll().stream()
                        .map(ShopDtoConverter::convertShopToDto)
                        .toList();}
                case "name" -> {return serviceShop.readAllSortByName().stream()
                        .map(ShopDtoConverter::convertShopToDto)
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
    public ShopDto create(@RequestBody ShopDto shopDto) {
        log.debug("ControllerCategory 'Create'");
        return ShopDtoConverter.convertShopToDto(serviceShop.create(shopDto));
    }

    @Override
    @GetMapping("/{id}")
    public ShopDto read(@PathVariable Long id) {
        log.debug("ControllerCategory 'Read'");
        return ShopDtoConverter.convertShopToDto(serviceShop.read(id));
    }

    @Override
    @PutMapping("/{id}")
    public ShopDto update(@PathVariable Long id, @RequestBody ShopDto shopDto) {
        log.debug("ControllerCategory 'Update'");
        return ShopDtoConverter.convertShopToDto(serviceShop.update(id, shopDto));
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.debug("ControllerCategory 'Delete'");
        serviceShop.delete(id);
    }
}
