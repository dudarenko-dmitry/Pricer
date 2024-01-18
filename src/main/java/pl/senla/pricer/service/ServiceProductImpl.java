package pl.senla.pricer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.senla.pricer.dao.DaoCategory;
import pl.senla.pricer.dao.DaoProduct;
import pl.senla.pricer.dto.ProductDto;
import pl.senla.pricer.entity.Product;
import pl.senla.pricer.exception.ProductByIdNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ServiceProductImpl implements ServiceProduct {

    @Autowired
    private DaoProduct daoProduct;

    @Autowired
    private DaoCategory daoCategory;

    @Override
    public List<Product> readAll() {
        log.debug("Start ServiceProduct 'ReadAll'");
        List<Product> products = daoProduct.findAll();
        if (products.isEmpty()) {
            log.info("List of Products is empty");
        }
        return products;
    }

    @Override
    public List<Product> readAllByOrderByName() {
        log.debug("Start ServiceProduct 'ReadAll'");
        List<Product> products = daoProduct.findAllByOrderByName();
        if (products.isEmpty()) {
            log.info("List of Products is empty");
        }
        return products;
    }

    @Override
    public List<Product> readAllByOrderByCategory() {
        log.debug("Start ServiceProduct 'ReadAll'");
        List<Product> products = daoProduct.findAllByOrderByCategory();
        if (products.isEmpty()) {
            log.info("List of Products is empty");
        }
        return products;
    }

    @Override
    public Product create(ProductDto productDto) {
        log.debug("Start ServiceProduct 'Create'");
        if (daoProduct.findByName(productDto.getName()) == null) {
            Product productNew = new Product();
            productNew.setName(productDto.getName());
            productNew.setCategory(daoCategory.findByName(productDto.getCategoryName()));
            return daoProduct.save(productNew);
        }
        log.info("Such Product is already exists.");
        return null;
    }

    @Override
    public Product read(Long id) {
        log.debug("Start ServiceProduct 'Read by ID'");
        return daoProduct.findById(id)
                .orElseThrow(() -> new ProductByIdNotFoundException(id));
    }

    @Override
    public Product readByName(String name) {
        log.debug("Start ServiceProduct 'Read by ID'");
        Product product = daoProduct.findByName(name);
        if (product == null) {
            log.info("Product with name {} not found", name);
        }
        return product;
    }

    @Override
    public Product update(Long id, ProductDto productDto) {
        log.debug("Start ServiceProduct 'Update'");
        Optional<Product> product = daoProduct.findById(id);
        if (product.isPresent()) {
            Product productUpdate = product.get();
            productUpdate.setName(productDto.getName());
            productUpdate.setCategory(daoCategory.findByName(productDto.getCategoryName()));
            return daoProduct.save(productUpdate);
        }
        log.info("Product with id {} not found", id);
        return null;
    }

    @Override
    public void delete(Long id) {
        log.debug("Start ServiceProduct 'Delete by ID'");
        if (!daoProduct.existsById(id)) {
            log.info("Product with id {} not found", id);
        }
        log.debug("Product was deleted");
        daoProduct.deleteById(id);
    }

}
