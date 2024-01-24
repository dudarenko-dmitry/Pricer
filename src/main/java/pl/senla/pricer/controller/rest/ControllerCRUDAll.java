package pl.senla.pricer.controller.rest;

import java.util.List;
import java.util.Map;

public interface ControllerCRUDAll<T> {

    List<T> readAll(Map<String,String> requestParams);
    T create(T t);
    T read(Long id);
    T update(Long id, T t);
    void delete(Long id);
}
