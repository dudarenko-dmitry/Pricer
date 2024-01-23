package pl.senla.pricer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.senla.pricer.entity.PriceTracking;

import java.util.List;

@Repository
public interface DaoPriceTracking  extends JpaRepository<PriceTracking, Long> {

    //	Ability to link the price to a specific product in a store at the current moment.moment
    @Query(value = "SELECT " +
            "pt.id, pt.product_id, pt.shop_id, pt.price, pt.registration_date " +
            "FROM price_tracking pt " +
            "WHERE pt.product_id=? AND " +
            "pt.shop_id=? AND " +
            "pt.registration_date=?",
            nativeQuery = true)
    List<PriceTracking> findPriceForProductShopDate(Long productId, Long shopId, String regDate);

//      Ability to track price dynamics for a specific product in a given period.
//"SELECT p.name, pt.shop_id, pt.price, pt.registration_date FROM pricer.price_tracking pt LEFT JOIN pricer.product p ON pt.product_id = p.id WHERE pt.registration_date BETWEEN '2023-01-01' AND '2023-01-04'""



//    List<PriceTracking> findAllWithParams();
//
//    @Query(value = "SELECT * FROM ")
//    List<PriceTracking> findAllByOrderByProductName();
//    List<PriceTracking> findAllByOrderByShopAddress();
//    List<PriceTracking> findAllByOrderByDate();
//
//    List<PriceTracking> findAllByProductName(String name);
//    List<PriceTracking> findAllByProductNameAndShopAddress(String name, String address);
//    List<PriceTracking> findAllByProductNameAndShopAddressInPeriod(String name, String address,
//                                                             String startDateString,String endDateString);
//    PriceTracking findAllByProductNameAndShopAddressAtDate(String name, String address,
//                                                           String dataString);
//
//    List<PriceTracking> findAllByShopAddressAtDate(String address, String dataString);
//    List<PriceTracking> findAllByRegistrationDate(String dateString);
}






//      SORT by NAME
//SELECT p.name, pt.shop_id, pt.price, pt.registration_date  FROM pricer.price_tracking pt
//LEFT JOIN pricer.product p ON pt.product_id = p.id
//ORDER BY p.name

//      SORT by Date
//SELECT p.name, pt.shop_id, pt.price, pt.registration_date  FROM pricer.price_tracking pt
//LEFT JOIN pricer.product p ON pt.product_id = p.id
//ORDER BY pt.registration_date



//       compare price in 2 shops on 1 date. CHANGE!!!
//SELECT p.name, pt1.shop_id, pt1.price, pt1.registration_date
//FROM pricer.price_tracking pt1
//LEFT JOIN pricer.product p ON pt1.product_id = p.id
//WHERE pt1.shop_id = 1 or pt1.shop_id = 2
//HAVING pt1.registration_date='2023-1-4'

//       compare price in 2+ shops on 1 date.
//SELECT
//pt.product_id,
//pt.shop_id,
//pt.price,
//pt.registration_date
//FROM price_tracking pt
//WHERE pt.shop_id IN (:shops)
//AND pt.registration_date = '2023-1-1'



// shop_id from SET
//@Query("SELECT e FROM Entity e WHERE e.id IN (:ids)")
//Set<Entity> getAllById(Set<Integer> ids);