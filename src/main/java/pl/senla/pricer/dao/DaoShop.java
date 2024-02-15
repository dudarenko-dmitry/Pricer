package pl.senla.pricer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.senla.pricer.entity.Shop;

import java.util.List;

@Repository
public interface DaoShop  extends JpaRepository<Shop, Long> {

    Shop findByAddress(String address);
    List<Shop> findAllByName(String name);
    List<Shop> findAllByOrderByName();
    List<Shop> findAllByOrderByAddress();
}
