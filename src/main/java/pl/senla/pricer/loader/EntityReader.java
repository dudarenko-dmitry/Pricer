package pl.senla.pricer.loader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class EntityReader<T> {
//
//    @Autowired
//    private final ConverterEntity<T> converter;

//    public EntityReader(ConverterEntity<T> converter) {
//        this.converter = converter;
//    }

    public List<T> load(String filePath, ConverterEntity<T> converter) throws IOException {
        List<T> list = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String csvT;
            int i = 0;
            while((csvT = bufferedReader.readLine()) != null) {
                if (i != 0) {
                    list.add(converter.convertStringToEntity(csvT));
                }
                i++;
            }
        }
        return list;
    }

}
