//package pl.senla.pricer.controller.rest;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import pl.senla.pricer.dto.ProductDto;
//import pl.senla.pricer.service.ServiceProduct;
//import pl.senla.pricer.utils.ProductDtoConverter;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/products")
//@Slf4j
//public class ControllerProductImpl implements ControllerProduct {
//
//    @Autowired
//    private ServiceProduct serviceProduct;
//
//    @Override
//    @GetMapping
//    public List<ProductDto> readAll(@RequestParam Map<String, String> requestParams) {
//        log.debug("ControllerProduct 'ReadAll'");
//        if (requestParams.isEmpty()) {
//            return serviceProduct.readAll(requestParams).stream()
//                    .map(ProductDtoConverter::convertProductToDto)
//                    .toList();
//        }
//        String name = requestParams.get("name");
//        if (name != null) {
//            return Collections.singletonList(ProductDtoConverter.convertProductToDto(serviceProduct.readByName(name)));
//        }
//        return getListWithParameters(requestParams);
//    }
//
//    @Override
//    @PostMapping("/")
//    public ProductDto create(@RequestBody ProductDto productDto) {
//        log.debug("ControllerProduct 'Create'");
//        return ProductDtoConverter.convertProductToDto(serviceProduct.create(productDto));
//    }
//
//    @Override
//    @GetMapping("/{id}")
//    public ProductDto read(@PathVariable Long id) {
//        log.debug("ControllerProduct 'Read'");
//        return ProductDtoConverter.convertProductToDto(serviceProduct.read(id));
//    }
//
//    @Override
//    @PutMapping("/{id}")
//    public ProductDto update(@PathVariable Long id, @RequestBody ProductDto productDto) {
//        log.debug("ControllerProduct 'Update'");
//        return ProductDtoConverter.convertProductToDto(serviceProduct.update(id, productDto));
//    }
//
//    @Override
//    @DeleteMapping("/{id}")
//    public void delete(@PathVariable Long id) {
//        log.debug("ControllerProduct 'Delete'");
//        serviceProduct.delete(id);
//    }
//
//    private List<ProductDto> getListWithParameters(Map<String, String> requestParams) {
//        String sort = requestParams.get("sort");
//        String categoryName = requestParams.get("category");
//        boolean isCategorySelected = categoryName != null;
//        List<ProductDto> products;
//        if (sort == null) {
//            if (isCategorySelected) {
//                products = serviceProduct.readAll(requestParams).stream()
//                        .filter(p -> p.getCategory().getName().equals(categoryName))
//                        .map(ProductDtoConverter::convertProductToDto)
//                        .toList();
//            } else {
//                products = serviceProduct.readAll(requestParams).stream()
//                        .map(ProductDtoConverter::convertProductToDto)
//                        .toList();
//            }
//        } else {
//            switch (sort) {
//                case "name":
//                    if (isCategorySelected) {
//                        products = serviceProduct.readAllByOrderByName().stream()
//                                .filter(p -> p.getCategory().getName().equals(categoryName))
//                                .map(ProductDtoConverter::convertProductToDto)
//                                .toList();
//                    } else {
//                        products = serviceProduct.readAllByOrderByName().stream()
//                                .map(ProductDtoConverter::convertProductToDto)
//                                .toList();
//                    }
//                    break;
//                case "category":
//                    if (isCategorySelected) {
//                        products = serviceProduct.readAllByOrderByCategory().stream()
//                                .filter(p -> p.getCategory().getName().equals(categoryName))
//                                .map(ProductDtoConverter::convertProductToDto)
//                                .toList();
//                    } else {
//                        products = serviceProduct.readAllByOrderByCategory().stream()
//                                .map(ProductDtoConverter::convertProductToDto)
//                                .toList();
//                    }
//                    break;
//                default:
//                    if (isCategorySelected) {
//                        products = serviceProduct.readAll(requestParams).stream()
//                                .filter(p -> p.getCategory().getName().equals(categoryName))
//                                .map(ProductDtoConverter::convertProductToDto)
//                                .toList();
//                    } else {
//                        products = serviceProduct.readAll(requestParams).stream()
//                                .map(ProductDtoConverter::convertProductToDto)
//                                .toList();
//                    }
//            }
//        }
//        return products;
//    }
//
//}
