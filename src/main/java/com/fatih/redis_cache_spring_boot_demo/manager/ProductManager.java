package com.fatih.redis_cache_spring_boot_demo.manager;

import com.fatih.redis_cache_spring_boot_demo.dao.ProductDao;
import com.fatih.redis_cache_spring_boot_demo.entity.ProductEntity;
import com.fatih.redis_cache_spring_boot_demo.manager.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductManager implements ProductService {

    private final ProductDao productDao;

    @CacheEvict(cacheNames = "products", allEntries = true)
    @Transactional
    @Override
    public ProductEntity createProduct(ProductEntity requestedProduct) {

        return productDao.save(requestedProduct);
    }

    @Caching(put = {@CachePut(cacheNames = "product_id", key = "'getProductById' + #result.id", unless = "#result == null")},
            evict = {@CacheEvict(cacheNames = "products", allEntries = true)})
    @Transactional
    @Override
    public ProductEntity updateProduct(ProductEntity requestedProduct) {

        ProductEntity foundProduct = getProductById(requestedProduct.getId());

        ProductEntity updatedProduct = checkUpdateConditions(requestedProduct, foundProduct);

        return productDao.save(updatedProduct);
    }

    @Cacheable(cacheNames = "product_id", key = "#root.methodName +  #id", unless = "#result == null")
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public ProductEntity getProductById(UUID id) {

        return productDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @CacheEvict(cacheNames = {"products", "product_id"}, allEntries = true)
    @Transactional
    @Override
    public void deleteProduct(UUID id) {

        ProductEntity foundProduct = getProductById(id);

        productDao.delete(foundProduct);
    }

    @Cacheable(cacheNames = "products", key = "#root.methodName", unless = "#result == null")
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<ProductEntity> getAllProducts(Pageable pageable) {

        Page<ProductEntity> foundProducts = productDao.findAll(pageable);

        return foundProducts.getContent();
    }

    @Transactional
    protected ProductEntity checkUpdateConditions(ProductEntity requestedProduct, ProductEntity foundProduct) {

        if (requestedProduct.getProductName() != null) {
            foundProduct.setProductName(requestedProduct.getProductName());
        }
        if (requestedProduct.getProductDescription() != null) {
            foundProduct.setProductDescription(requestedProduct.getProductDescription());
        }
        if (requestedProduct.getPrice() != null) {
            foundProduct.setPrice(requestedProduct.getPrice());
        }
        if (requestedProduct.getStockQuantity() != null) {
            foundProduct.setStockQuantity(requestedProduct.getStockQuantity());
        }

        return foundProduct;
    }
}