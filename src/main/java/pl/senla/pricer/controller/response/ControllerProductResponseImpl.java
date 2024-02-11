package pl.senla.pricer.controller.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.senla.pricer.dto.ProductDto;
import pl.senla.pricer.entity.Product;
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
    @PreAuthorize("hasAuthority('read')")
    @GetMapping
    public ResponseEntity<String> readAll(@RequestParam Map<String, String> requestParams) {
        log.debug("ControllerProduct 'ReadAll'");
        try {
            List<ProductDto> list = serviceProduct.readAll(requestParams).stream()
                    .map(ProductDtoConverter::convertProductToDto)
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
    public ResponseEntity<String> create(@RequestBody ProductDto productDto) {
        log.debug("ControllerProduct 'Create'");
        try {
            Product productNew = serviceProduct.create(productDto);
            if (productNew != null) {
                ProductDto productDtoNew = ProductDtoConverter.convertProductToDto(productNew);
                return new ResponseEntity<>(productDtoNew.toString(), HttpStatus.CREATED);
            }
            return new ResponseEntity<>(new ProductNotCreatedException().toString(), HttpStatus.OK);
        } catch (RuntimeException e) {
            log.warn(e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @PostMapping("/load/")
    public ResponseEntity<String> createFromFile(@RequestBody String filePath) {
        log.debug("ControllerProduct 'createFromFile'");
        try {
            System.out.println("filePath: " + filePath);
            List<ProductDto> list = serviceProduct.createFromFile(filePath).stream()
                    .map(ProductDtoConverter::convertProductToDto)
                    .toList();
            if (!list.isEmpty()) {
                return new ResponseEntity<>(list.toString(), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ProductNotCreatedException().toString(), HttpStatus.OK);
        } catch (RuntimeException e) {
            log.warn(e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<String> read(@PathVariable Long id) {
        log.debug("ControllerProduct 'Read'");
        try {
            Product product = serviceProduct.read(id);
            if (product != null) {
                ProductDto productDto = ProductDtoConverter.convertProductToDto(product);
                return new ResponseEntity<>(productDto.toString(), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ProductByIdNotFoundException(id).toString(), HttpStatus.OK);
        } catch (RuntimeException e) {
            log.warn(e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody ProductDto product) {
        log.debug("ControllerProduct 'Update'");
        try {
            Product productUpdate = serviceProduct.update(id, product);
            if (productUpdate != null) {
                ProductDto productDtoUpdate = ProductDtoConverter
                        .convertProductToDto(productUpdate);
                return new ResponseEntity<>(productDtoUpdate.toString(), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ProductNotCreatedException().toString(), HttpStatus.OK);
        } catch (RuntimeException e) {
            log.warn(e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        log.debug("ControllerProduct 'Delete'");
        try {
            if (serviceProduct.read(id) != null) {
                serviceProduct.delete(id);
                return new ResponseEntity<>("Product id " + id + " was deleted.", HttpStatus.OK);
            }
            return new ResponseEntity<>(new ProductByIdNotFoundException(id).toString(), HttpStatus.OK);
        } catch (RuntimeException e) {
            log.warn(e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
