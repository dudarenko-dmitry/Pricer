package pl.senla.pricer.service;

import java.util.List;
import java.util.Map;

public interface ServiceCRUDAll<T, TDto> {

    List<T> readAll(Map<String, String> requestParams);
    T create(TDto tdto);
    T read(Long id);
    T update(Long id, TDto tdto);
    void delete(Long id);

}
