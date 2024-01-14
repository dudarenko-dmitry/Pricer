package pl.senla.pricer.service;

import java.util.List;

public interface ServiceCRUDAll<T,Tdto> {

    List<T> readAll();
    T create(Tdto tdto);
    T read(Long id);
    T update(Long id, Tdto tdto);
    boolean delete(Long id);

}
