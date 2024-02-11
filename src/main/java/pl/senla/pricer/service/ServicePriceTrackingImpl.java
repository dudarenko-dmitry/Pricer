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
import pl.senla.pricer.exception.PriceTrackingNotFoundException;
import pl.senla.pricer.loader.DataLoader;

import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
public class ServicePriceTrackingImpl implements ServicePriceTracking {

    @Autowired
    private DaoPriceTracking daoPriceTracking;
    @Autowired
    private DaoProduct daoProduct;
    @Autowired
    private DaoShop daoShop;
    @Autowired
    private DataLoader<PriceTrackingDto> priceTrackingDtoDataLoader;

    @Override
    public List<PriceTracking> readAll(Map<String, String> requestParams) {
        log.debug("Start ServiceProduct 'ReadAll'");
        List<PriceTracking> prices = daoPriceTracking.findAll();
        if (prices.isEmpty()) {
            log.info("List of Prices is empty");
            return prices;
        }

        if (requestParams.isEmpty()) {
            return prices;
        } else {
            log.debug("Start ServiceProduct 'ReadAll With Params'");
            String productName = requestParams.get("product_name");
            String shopAddress1 = requestParams.get("shop_address1");
            String shopAddress2 = requestParams.get("shop_address2");
            String registrationDateString = requestParams.get("registration_date");
            if (productName != null &&
                    shopAddress1 != null &&
                    shopAddress2 == null &&
                    registrationDateString != null) {
                Long productId = daoProduct.findByName(productName).getId();
                Long shopId1 = daoShop.findByAddress(shopAddress1).getId();
                prices = Collections.singletonList(daoPriceTracking
                        .findPriceForProductShopDate(productId, registrationDateString, shopId1));
            } else if (productName != null &&
                    shopAddress1 != null &&
                    shopAddress2 != null &&
                    registrationDateString != null) {
                Long productId = daoProduct.findByName(productName).getId();
                Long shopId1 = daoShop.findByAddress(shopAddress1).getId();
                Long shopId2 = daoShop.findByAddress(shopAddress2).getId();
                prices = daoPriceTracking
                        .findPricesForProductIn2ShopsAtDate(productId, registrationDateString, shopId1, shopId2);
            }
        }
        return prices;
    }

    @Override
    public PriceTracking create(PriceTrackingDto priceTrackingDto) {
        log.debug("Start ServiceProduct 'Create'");
        String productName = priceTrackingDto.getProductName();
        Product product = daoProduct.findByName(productName);
        String address = priceTrackingDto.getAddress();
        Shop shop = daoShop.findByAddress(address);
        String dateString = priceTrackingDto.getDateString();
        LocalDate registration_date = convertStringToDate(dateString);
        Map<String, String> requestParams = new HashMap<>();
        boolean isPresentPriceTracking = readAll(requestParams).stream()
                .anyMatch(p -> p.getProduct().equals(product) &&
                        p.getShop().equals(shop) &&
                        p.getDate().equals(registration_date));
        if (!isPresentPriceTracking) {
            if (product != null && shop != null) {
                PriceTracking priceTracking = new PriceTracking();
                priceTracking.setProduct(product);
                priceTracking.setShop(shop);
                priceTracking.setPrice(priceTrackingDto.getPrice());
                priceTracking.setDate(registration_date);
                return daoPriceTracking.save(priceTracking);
            }
        }
        log.info("Such Price is already registered.");
        return null;
    }

    @Override
    public List<PriceTracking> createFromFile(String filePath) {
        log.debug("Start ServicePriceTracking 'createFromFile'");
        List<PriceTrackingDto> priceTrackingDtoList = priceTrackingDtoDataLoader.loadData(filePath);
        List<PriceTracking> priceTrackingList = new ArrayList<>();
        for(PriceTrackingDto priceTrackingDto : priceTrackingDtoList) {
            Long productId = daoProduct.findByName(priceTrackingDto.getProductName()).getId();
            String regDate = priceTrackingDto.getDateString();
            Long shopId = daoShop.findByAddress(priceTrackingDto.getAddress()).getId();
            PriceTracking priceTrackingNew = daoPriceTracking.findPriceForProductShopDate(productId, regDate, shopId);
            if (priceTrackingNew == null) {
                priceTrackingNew = new PriceTracking();
                priceTrackingNew.setProduct(daoProduct.getReferenceById(productId));
                priceTrackingNew.setPrice(priceTrackingDto.getPrice());
                priceTrackingNew.setShop(daoShop.getReferenceById(shopId));
                priceTrackingNew.setDate(convertStringToDate(regDate));
                log.debug("Add new PriceTracking to List");
                priceTrackingList.add(priceTrackingNew);
            }
            log.info("Such PriceTracking {} is already exists", priceTrackingNew);
        }
        if (!priceTrackingList.isEmpty()) {
            log.debug("Save new PriceTrackings' List");
            return daoPriceTracking.saveAll(priceTrackingList);
        }
        log.debug("List of new PriceTrackings is empty");
        return priceTrackingList;
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
            String productName = priceTrackingDto.getProductName();
            Product product = daoProduct.findByName(productName);
            String address = priceTrackingDto.getAddress();
            Shop shop = daoShop.findByAddress(address);
            String dateString = priceTrackingDto.getDateString();
            LocalDate registration_date = convertStringToDate(dateString);
            if (product != null && shop != null) {
                PriceTracking priceTrackingNew = PriceTracking.builder()
                        .id(id)
                        .product(product)
                        .shop(shop)
                        .price(priceTrackingDto.getPrice())
                        .date(registration_date)
                        .build();
                return daoPriceTracking.save(priceTrackingNew);
            }
            log.warn("Product or Shop does not exist. Please, check input data.");
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

    @Override
    public Map<LocalDate, Integer> getPriceDynamic(Map<String, String> requestParams) {
        log.debug("Start ServiceProduct 'GetPriceDynamic'");
        String productName = requestParams.get("product_name");
        String startDateString = requestParams.get("start_date");
        String endDateString = requestParams.get("end_date");

        List<PriceTracking> prices = daoPriceTracking.findAll();
        if (prices.isEmpty()) {
            log.info("List of Prices is empty");
        } else if (
                productName != null &&
                        startDateString != null &&
                        endDateString != null) {
            Long productId = daoProduct.findByName(productName).getId();
            List<PriceTracking> pricesForProductInPeriod = daoPriceTracking
                    .findPricesForProductInPeriod(productId, startDateString, endDateString);
            return findMinPrices(pricesForProductInPeriod);
        }
        return null;
    }

    private Map<LocalDate, Integer> findMinPrices(List<PriceTracking> pricesForProductInPeriod) {
        List<LocalDate> dates = pricesForProductInPeriod.stream()
                .map(PriceTracking::getDate)
                .distinct()
                .toList();
        Map<LocalDate, Integer> minPrices = new TreeMap<>();
        for (LocalDate date : dates) {
            Integer price = pricesForProductInPeriod.stream()
                    .filter(pt -> pt.getDate().isEqual(date))
                    .map(PriceTracking::getPrice)
                    .min(Comparator.comparingInt(p -> p))
                    .orElseThrow(() -> new PriceTrackingNotFoundException(date));
            minPrices.put(date, price);
        }
        return minPrices;
    }

    private LocalDate convertStringToDate(String dateString) {
        String[] dateSplit = dateString.split("-");
        return LocalDate.of(
                Integer.parseInt(dateSplit[0]),
                Integer.parseInt(dateSplit[1]),
                Integer.parseInt(dateSplit[2]));
    }

}
