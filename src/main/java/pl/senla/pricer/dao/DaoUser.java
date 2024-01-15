package pl.senla.pricer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.senla.pricer.entity.User;

@Repository
public interface DaoUser extends JpaRepository<User, Long> {

}
