package pl.senla.pricer.controller.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.senla.pricer.dto.ShopDto;
import pl.senla.pricer.entity.Shop;
import pl.senla.pricer.exception.ShopNotFoundException;
import pl.senla.pricer.exception.ShopNotCreatedException;
import pl.senla.pricer.service.ServiceShop;
import pl.senla.pricer.utils.ShopDtoConverter;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("v2/shops")
@Slf4j
public class ControllerShopResponseImpl implements ControllerShopResponse {

    @Autowired
    private ServiceShop serviceShop;

    @Override
    @PreAuthorize("hasAuthority('read')")
    @GetMapping
    public ResponseEntity<String> readAll(@RequestParam Map<String,String> requestParams) {
        log.debug("ControllerShop 'ReadAll'");
        try {
            List<ShopDto> list = serviceShop.readAll(requestParams).stream()
                    .map(ShopDtoConverter::convertShopToDto)
                    .toList();
            if (list.isEmpty()) {
                return new ResponseEntity<>("list is empty", HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(list.toString(), HttpStatus.OK);
        } catch (RuntimeException e) {
            log.warn(e.toString());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @PreAuthorize("hasAuthority('write')")
    @PostMapping("/")
    public ResponseEntity<String> create(@RequestBody ShopDto shopDto) {
        log.debug("ControllerCategory 'Create'");
        try {
            Shop shopNew = serviceShop.create(shopDto);
            if (shopNew != null) {
                ShopDto shopDtoNew = ShopDtoConverter.convertShopToDto(shopNew);
                return new ResponseEntity<>(shopDtoNew.toString(), HttpStatus.CREATED);
            }
            return new ResponseEntity<>(new ShopNotCreatedException().toString(), HttpStatus.OK);
        } catch (RuntimeException e) {
            log.warn(e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @PreAuthorize("hasAuthority('read')")
    @GetMapping("/{id}")
    public ResponseEntity<String> read(@PathVariable Long id) {
        log.debug("ControllerCategory 'Read'");
        try {
            Shop shop = serviceShop.read(id);
            if (shop != null) {
                ShopDto shopDto = ShopDtoConverter.convertShopToDto(shop);
                return new ResponseEntity<>(shopDto.toString(), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ShopNotFoundException(id).toString(), HttpStatus.OK);
        } catch (RuntimeException e) {
            log.warn(e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @PreAuthorize("hasAuthority('update')")
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody ShopDto shop) {
        log.debug("ControllerCategory 'Update'");
        try {
            Shop shopUpdate = serviceShop.update(id, shop);
            if (shopUpdate != null) {
                ShopDto shopDtoUpdate = ShopDtoConverter.convertShopToDto(shopUpdate);
                return new ResponseEntity<>(shopDtoUpdate.toString(), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ShopNotCreatedException().toString(), HttpStatus.OK);
        } catch (RuntimeException e) {
            log.warn(e.toString());
            return new ResponseEntity<>(new ShopNotCreatedException().toString(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @Override
    @PreAuthorize("hasAuthority('delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        log.debug("ControllerCategory 'Delete'");
        try {
            if (serviceShop.read(id) != null) {
                serviceShop.delete(id);
                return new ResponseEntity<>("Shop id " + id + " was deleted.", HttpStatus.OK);
            }
            return new ResponseEntity<>(new ShopNotFoundException(id).toString(), HttpStatus.OK);
        } catch (RuntimeException e) {
            log.warn(e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
