package pl.senla.pricer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.senla.pricer.entity.Product;

import java.util.List;

@Repository
public interface DaoProduct  extends JpaRepository<Product, Long> {

    Product findByName(String name);
    List<Product> findAllByOrderByName();
    List<Product> findAllByOrderByCategory();

    @Query(value = "SELECT p.id, p.name, p.category_id " +
            "FROM product p JOIN " +
            "(SELECT c.id, c.name FROM category c " +
            "WHERE c.name=?) as c2 " +
            "ON  p.category_id=c2.id")
    List<Product> findAllByCategoryName(String categoryName);

    @Query(value = "SELECT p.id, p.name, p.category_id " +
            "FROM product p JOIN " +
            "(SELECT c.id, c.name FROM category c " +
            "WHERE c.name=?) as c2 " +
            "ON  p.category_id=c2.id" +
            "ORDER BY p.name")
    List<Product> findAllByCategoryNameByOrderByName(String categoryName);

    @Query(value = "SELECT p.id, p.name, p.category_id " +
            "FROM product p JOIN " +
            "(SELECT c.id, c.name FROM category c " +
            "ON p.category_id=c.id" +
            "ORDER BY c.name")
    List<Product> findAllByOrderByCategoryName();

}
