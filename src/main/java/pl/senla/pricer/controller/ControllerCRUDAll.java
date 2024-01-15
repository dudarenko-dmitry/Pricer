package pl.senla.pricer.controller;

import java.util.List;

public interface ControllerCRUDAll<T> {

    List<T> readAll(String sortBy, String filter, String startDate, String endDate);
    T create(T t);
    T read(Long id);
    T update(Long id, T t);
    void delete(Long id);
}
