package pl.senla.pricer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.senla.pricer.entity.Product;

import java.util.List;

@Repository
public interface DaoProduct  extends JpaRepository<Product, Long> {

    Product findByName(String name);
    List<Product> findAllByOrderByName();
    List<Product> findAllByOrderByCategory();

}
