package pl.senla.pricer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.senla.pricer.entity.Shop;

@Repository
public interface DaoShop  extends JpaRepository<Shop, Long> {
}
