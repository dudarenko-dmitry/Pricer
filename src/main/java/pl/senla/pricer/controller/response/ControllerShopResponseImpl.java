package pl.senla.pricer.controller.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.senla.pricer.dto.ShopDto;
import pl.senla.pricer.exception.CategoryByIdNotFoundException;
import pl.senla.pricer.exception.ShopByIdNotFoundException;
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
    @GetMapping
    public ResponseEntity<String> readAll(@RequestParam Map<String,String> requestParams) {
        log.debug("ControllerShop 'ReadAll'");
        try {
            List<ShopDto> list = serviceShop.readAll(requestParams).stream()
                    .map(ShopDtoConverter::convertShopToDto)
                    .toList();
            return new ResponseEntity<>(list.toString(), HttpStatus.OK);
        } catch (ShopByIdNotFoundException e) {
            log.debug(e.toString());
            return new ResponseEntity<>("Could not read Shops' list", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @PostMapping("/")
    public ResponseEntity<String> create(@RequestBody ShopDto shopDto) {
        log.debug("ControllerCategory 'Create'");
        try {
            ShopDto shopDtoNew = ShopDtoConverter.convertShopToDto(serviceShop.create(shopDto));
            return new ResponseEntity<>(shopDtoNew.toString(), HttpStatus.CREATED);
        } catch (ShopNotCreatedException e) {
            log.debug(e.toString());
            return new ResponseEntity<>("Shop not created", HttpStatus.FORBIDDEN);
        }
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<String> read(@PathVariable Long id) {
        log.debug("ControllerCategory 'Read'");
        try {
            ShopDto shopDto = ShopDtoConverter.convertShopToDto(serviceShop.read(id));
            return new ResponseEntity<>(shopDto.toString(), HttpStatus.OK);
        } catch (ShopByIdNotFoundException e) {
            log.debug(e.toString());
            return new ResponseEntity<>("Shop not found.", HttpStatus.NO_CONTENT);
        }
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody ShopDto shopDto) {
        log.debug("ControllerCategory 'Update'");
        try {
            ShopDto shopDtoUpdate = ShopDtoConverter.convertShopToDto(serviceShop.update(id, shopDto));
            return new ResponseEntity<>(shopDtoUpdate.toString(), HttpStatus.OK);
        } catch (ShopByIdNotFoundException | ShopNotCreatedException e) {
            log.debug(e.toString());
            return new ResponseEntity<>("Shop not updated.", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        log.debug("ControllerCategory 'Delete'");
        try {
            serviceShop.delete(id);
            return new ResponseEntity<>("Shop was deleted.", HttpStatus.OK);
        } catch (CategoryByIdNotFoundException e) {
            log.debug(e.toString());
            return new ResponseEntity<>("Shop not found", HttpStatus.NO_CONTENT);
        }
    }
}
