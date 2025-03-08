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

/**
 * Service class for managing product-related operations.
 * Implements caching mechanisms for better performance.
 */
@Service
@RequiredArgsConstructor
public class ProductManager implements ProductService {

    private final ProductDao productDao;

    /**
     * Creates a new product and clears the product cache.
     *
     * @param requestedProduct the product entity to be created
     * @return the saved ProductEntity
     */
    @CacheEvict(cacheNames = "products", allEntries = true)
    @Transactional
    @Override
    public ProductEntity createProduct(ProductEntity requestedProduct) {

        return productDao.save(requestedProduct);
    }

    /**
     * Updates an existing product and updates the cache.
     *
     * @param requestedProduct the product entity with updated details
     * @return the updated ProductEntity
     */
    @Caching(put = {@CachePut(cacheNames = "product_id", key = "'getProductById' + #result.id", unless = "#result == null")},
            evict = {@CacheEvict(cacheNames = "products", allEntries = true)})
    @Transactional
    @Override
    public ProductEntity updateProduct(ProductEntity requestedProduct) {

        ProductEntity foundProduct = getProductById(requestedProduct.getId());

        ProductEntity updatedProduct = checkUpdateConditions(requestedProduct, foundProduct);

        return productDao.save(updatedProduct);
    }

    /**
     * Retrieves a product by its ID with caching support.
     *
     * @param id the unique identifier of the product
     * @return the found ProductEntity
     */
    @Cacheable(cacheNames = "product_id", key = "#root.methodName +  #id", unless = "#result == null")
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public ProductEntity getProductById(UUID id) {

        return productDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    /**
     * Deletes a product by its ID and clears related caches.
     *
     * @param id the unique identifier of the product to be deleted
     */
    @CacheEvict(cacheNames = {"products", "product_id"}, allEntries = true)
    @Transactional
    @Override
    public void deleteProduct(UUID id) {

        ProductEntity foundProduct = getProductById(id);

        productDao.delete(foundProduct);
    }

    /**
     * Retrieves all products with caching support.
     *
     * @param pageable pagination details
     * @return a list of ProductEntity
     */
    @Cacheable(cacheNames = "products", key = "#root.methodName", unless = "#result == null")
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<ProductEntity> getAllProducts(Pageable pageable) {

        Page<ProductEntity> foundProducts = productDao.findAll(pageable);

        return foundProducts.getContent();
    }

    /**
     * Checks and updates the product details before saving.
     *
     * @param requestedProduct the product entity with new details
     * @param foundProduct the existing product entity
     * @return the updated ProductEntity
     */
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