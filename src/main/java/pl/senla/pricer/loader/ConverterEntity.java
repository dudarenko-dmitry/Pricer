package pl.senla.pricer.loader;

public interface ConverterEntity<T> {

    T convertStringToEntity(String csvT);
}
