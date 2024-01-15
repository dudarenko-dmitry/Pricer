package pl.senla.pricer.service;

import java.util.List;

public interface ServiceCRUDAll<T, TDto> {

    List<T> readAll();
    T create(TDto tdto);
    T read(Long id);
    T update(Long id, TDto tdto);
    void delete(Long id);

}
