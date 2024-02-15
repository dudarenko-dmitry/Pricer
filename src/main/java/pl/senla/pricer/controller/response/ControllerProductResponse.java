package pl.senla.pricer.controller.response;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import pl.senla.pricer.dto.ProductDto;

public interface ControllerProductResponse extends ControllerCRUDAllResponse<ProductDto> {

    @PostMapping("/load/{filePath}")
    ResponseEntity<String> createFromFile(String filePath);
}
