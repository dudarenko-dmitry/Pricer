package pl.senla.pricer.loader;

import java.util.List;

public interface DataLoader<T> {

    List<T> loadData(String filePath);
}
