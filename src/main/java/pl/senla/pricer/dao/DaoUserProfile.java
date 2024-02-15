package pl.senla.pricer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.senla.pricer.entity.UserProfile;

@Repository
public interface DaoUserProfile extends JpaRepository<UserProfile, Long> {

    @Query(value = "SELECT up.id, up.first_name, up.last_name, up.user_id " +
            "FROM user_profile up " +
            "WHERE up.first_name=:first_name AND up.last_name=:last_name",
            nativeQuery = true)
    UserProfile findByFirstNameAndLastName(String first_name, String last_name);

}
