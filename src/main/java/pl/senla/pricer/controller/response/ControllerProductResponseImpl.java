package pl.senla.pricer.controller.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.senla.pricer.dto.ProductDto;
import pl.senla.pricer.exception.ProductByIdNotFoundException;
import pl.senla.pricer.exception.ProductNotCreatedException;
import pl.senla.pricer.service.ServiceProduct;
import pl.senla.pricer.utils.ProductDtoConverter;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("v2/products")
@Slf4j
public class ControllerProductResponseImpl implements ControllerProductResponse {

    @Autowired
    private ServiceProduct serviceProduct;

    @Override
    @GetMapping
    public ResponseEntity<String> readAll(@RequestParam Map<String, String> requestParams) {
        log.debug("ControllerProduct 'ReadAll'");
        try {
            List<ProductDto> products = serviceProduct.readAll(requestParams).stream()
                    .map(ProductDtoConverter::convertProductToDto)
                    .toList();
            return new ResponseEntity<>(products.toString(), HttpStatus.OK);
        } catch (ProductByIdNotFoundException e) {
            log.debug(e.toString());
            return new ResponseEntity<>("Could not read Products' list.", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @PostMapping("/")
    public ResponseEntity<String> create(@RequestBody ProductDto productDto) {
        log.debug("ControllerProduct 'Create'");
        try {
            ProductDto productDtoNew = ProductDtoConverter.convertProductToDto(serviceProduct.create(productDto));
            return new ResponseEntity<>(productDtoNew.toString(), HttpStatus.CREATED);
        } catch (ProductByIdNotFoundException e) {
            log.debug(e.toString());
            return new ResponseEntity<>("Product not created", HttpStatus.FORBIDDEN);
        }
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<String> read(@PathVariable Long id) {
        log.debug("ControllerProduct 'Read'");
        try {
            ProductDto productDto = ProductDtoConverter.convertProductToDto(serviceProduct.read(id));
            return new ResponseEntity<>(productDto.toString(), HttpStatus.OK);
        } catch (ProductByIdNotFoundException e) {
            log.debug(e.toString());
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody ProductDto productDto) {
        log.debug("ControllerProduct 'Update'");
        try {
            ProductDto productDtoUpdate = ProductDtoConverter.convertProductToDto(serviceProduct.update(id, productDto));
            return new ResponseEntity<>(productDtoUpdate.toString(), HttpStatus.OK);
        } catch (ProductByIdNotFoundException | ProductNotCreatedException e) {
            log.debug(e.toString());
            return new ResponseEntity<>("Product not updated", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        log.debug("ControllerProduct 'Delete'");
        try {
            serviceProduct.delete(id);
            return new ResponseEntity<>("Product was deleted.", HttpStatus.OK);
        } catch (ProductByIdNotFoundException e) {
            log.debug(e.toString());
            return new ResponseEntity<>("Product not deleted.", HttpStatus.NOT_FOUND);
        }
    }

}
