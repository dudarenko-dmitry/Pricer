package pl.senla.pricer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.senla.pricer.entity.Product;

@Repository
public interface DaoProduct  extends JpaRepository<Product, Long> {

}
