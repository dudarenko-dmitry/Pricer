package pl.senla.pricer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.senla.pricer.dao.DaoPriceTracking;
import pl.senla.pricer.dao.DaoProduct;
import pl.senla.pricer.dao.DaoShop;
import pl.senla.pricer.dto.PriceTrackingDto;
import pl.senla.pricer.entity.PriceTracking;
import pl.senla.pricer.entity.Product;
import pl.senla.pricer.entity.Shop;
import pl.senla.pricer.exception.PriceNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class ServicePriceTrackingImpl implements ServicePriceTracking{

    @Autowired
    private DaoPriceTracking daoPriceTracking;
    @Autowired
    private DaoProduct daoProduct;
    @Autowired
    private DaoShop daoShop;

    @Override
    public List<PriceTracking> readAll() {
        log.debug("Start ServiceProduct 'ReadAll'");
        List<PriceTracking> prices = daoPriceTracking.findAll();
        if (prices.isEmpty()) {
            log.info("List of Prices is empty");
        }
        return prices;
    }

    @Override
    public List<PriceTracking> readAllWithParams(Map<String, String> requestParams) {
        log.debug("Start ServiceProduct 'ReadAllWithParams'");
        String sort = requestParams.get("sort");
        String productId = requestParams.get("product_id");
        String shopId1 = requestParams.get("shop_id1");
        String shopId2 = requestParams.get("shop_id2");
        String registrationDateString = requestParams.get("registration_date");
        String startDateString = requestParams.get("start_date");
        String endDateString = requestParams.get("end_date");

        List<PriceTracking> prices = daoPriceTracking.findAll();
        if (prices.isEmpty()) {
            log.info("List of Prices is empty");
        } else if (sort == null &&
                productId != null &&
                shopId1 != null &&
                shopId2 == null &&
                registrationDateString != null &&
                startDateString == null &&
                endDateString == null) {
            prices = daoPriceTracking.findPriceForProductShopDate(Long.parseLong(productId), Long.parseLong(shopId1), registrationDateString);
        }
        return prices;
    }

    @Override
    public PriceTracking create(PriceTrackingDto priceTrackingDto) {
        log.debug("Start ServiceProduct 'Create'");
        Long productId = priceTrackingDto.getProductId();
        Long shopId = priceTrackingDto.getShopId();
        String dateString = priceTrackingDto.getDateString();
        LocalDate registration_date = convertStringToDate(dateString);
        boolean isPresentPriceTracking = readAll().stream()
                .anyMatch(p -> p.getProduct().getId().equals(productId) &&
                        p.getShop().getId().equals(shopId) &&
                        p.getDate().equals(registration_date));
        if (!isPresentPriceTracking) {
            Optional<Product> product = daoProduct.findById(productId);
            Optional<Shop> shop = daoShop.findById(shopId);
            if (product.isPresent() && shop.isPresent()) {
                PriceTracking priceTracking = PriceTracking.builder()
                        .id(null)
                        .product(product.get())
                        .shop(shop.get())
                        .price(priceTrackingDto.getPrice())
                        .date(registration_date)
                        .build();
                return daoPriceTracking.save(priceTracking);
            }
        }
        log.info("Such Price is already registered.");
        return null;
    }

    @Override
    public PriceTracking read(Long id) {
        log.debug("Start ServiceProduct 'Read by ID'");
        return daoPriceTracking.findById(id)
                .orElseThrow(() -> new PriceNotFoundException(id));
    }

    @Override
    public PriceTracking update(Long id, PriceTrackingDto priceTrackingDto) {
        log.debug("Start ServiceProduct 'Update'");
        if (daoPriceTracking.findById(id).isPresent()) {
            Long productId = priceTrackingDto.getProductId();
            Long shopId = priceTrackingDto.getShopId();
            String dateString = priceTrackingDto.getDateString();
            LocalDate registration_date = convertStringToDate(dateString);
            boolean isPresentPriceTracking = readAll().stream()
                    .anyMatch(p -> p.getProduct().getId().equals(productId) &&
                            p.getShop().getId().equals(shopId) &&
                            p.getDate().equals(registration_date));

            if (!isPresentPriceTracking) {
                Optional<Product> product = daoProduct.findById(productId);
                Optional<Shop> shop = daoShop.findById(shopId);
                if (product.isPresent() && shop.isPresent()) {
                    PriceTracking priceTrackingNew = PriceTracking.builder()
                            .id(null)
                            .product(product.get())
                            .shop(shop.get())
                            .price(priceTrackingDto.getPrice())
                            .date(registration_date)
                            .build();
                    return daoPriceTracking.save(priceTrackingNew);
                }
            }
            log.info("Such Price is already registered.");
            return null;
        }
        log.info("Price not found.");
        return null;
    }

    @Override
    public void delete(Long id) {
        log.debug("Start ServiceProduct 'Delete by ID'");
        if (daoPriceTracking.findById(id).isPresent()) {
            log.debug("Price was deleted.");
            daoPriceTracking.deleteById(id);
        } else {
            log.debug("PriceTracking with ID {} not found.", id);
        }
    }

    private LocalDate convertStringToDate(String dateString) {
        String[] dateSplit = dateString.split("-");
        return LocalDate.of(
                Integer.parseInt(dateSplit[0]),
                Integer.parseInt(dateSplit[1]),
                Integer.parseInt(dateSplit[2]));
    }

}
