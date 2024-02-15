package pl.senla.pricer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.senla.pricer.entity.Category;

import java.util.List;

@Repository
public interface DaoCategory extends JpaRepository<Category, Long> {

    List<Category> findAllByOrderByName();
    Category findByName(String name);

}
