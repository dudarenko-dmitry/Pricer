package pl.senla.pricer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.senla.pricer.entity.PriceTracking;

@Repository
public interface DaoPriceTracking  extends JpaRepository<PriceTracking, Long> {

}
