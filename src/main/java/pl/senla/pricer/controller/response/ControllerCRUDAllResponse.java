package pl.senla.pricer.controller.response;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ControllerCRUDAllResponse<T> {

    ResponseEntity<String> readAll(Map<String,String> requestParams);
    ResponseEntity<String> create(T t);
    ResponseEntity<String> read(Long id);
    ResponseEntity<String> update(Long id, T t);
    ResponseEntity<String> delete(Long id);
}
