package pl.senla.pricer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.senla.pricer.entity.UserProfile;

@Repository
public interface DaoUserProfile extends JpaRepository<UserProfile, Long> {

}
