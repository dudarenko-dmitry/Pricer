package pl.senla.pricer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.senla.pricer.entity.Category;

@Repository
public interface DaoCategory  extends JpaRepository<Category, Long> {
}
