package pl.senla.pricer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.senla.pricer.entity.PriceTracking;

import java.util.List;

@Repository
public interface DaoPriceTracking extends JpaRepository<PriceTracking, Long> {

    //	Ability to link the price to a specific product in a store at the current moment.
    @Query(value = "SELECT " +
            "pt.id, pt.product_id, pt.shop_id, pt.price, pt.registration_date " +
            "FROM price_tracking pt " +
            "WHERE pt.product_id=? AND " +
            "pt.registration_date=? AND " +
            "pt.shop_id=?",
            nativeQuery = true)
    List<PriceTracking> findPriceForProductShopDate(Long productId, String regDate, Long shopId);

    //      Ability to track price dynamics for a specific product in a given period.
    @Query(value = "SELECT " +
            "pt.id, pt.product_id, pt.shop_id, pt.price, pt.registration_date " +
            "FROM pricer.price_tracking pt " +
            "WHERE pt.product_id=? AND " +
            "pt.registration_date BETWEEN ? AND ? " +
            "ORDER BY pt.registration_date ASC",
            nativeQuery = true)
    List<PriceTracking> findPricesForProductInPeriod(Long productId, String startDateString, String endDateString);

    // Price comparison for items in different stores (at least two).
    @Query(value = "SELECT DISTINCT " +
            "pt.id, pt.product_id, pt.shop_id, pt.price, pt.registration_date " +
            "FROM price_tracking pt " +
            "WHERE pt.product_id=? AND " +
            "pt.registration_date=? AND " +
            "(pt.shop_id=? OR " +
            "pt.shop_id=?)",
            nativeQuery = true)
    List<PriceTracking> findPricesForProductIn2ShopsAtDate(Long productId, String regDate, Long shopId1, Long shopId2);

    //       compare price in 2+ shops on 1 date. // NOT READY YET !!!
//    @Query(value = "SELECT " +
//            "pt.product_id, pt.shop_id, pt.price, pt.registration_date " +
//            "FROM price_tracking pt " +
//            "WHERE pt.shop_id IN (:shops) AND " +
//            "pt.registration_date = '2023-1-1'",
//            nativeQuery = true)
//    List<PriceTracking> comparePricesForProductInShopsAndDate(Long productId, Set<Long> shopId, String regDate);

    // shop_id from SET
//@Query("SELECT e FROM Entity e WHERE e.id IN (:ids)")
//Set<Entity> getAllById(Set<Integer> ids);

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