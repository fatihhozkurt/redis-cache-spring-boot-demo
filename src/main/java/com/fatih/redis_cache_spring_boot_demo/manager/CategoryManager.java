package com.fatih.redis_cache_spring_boot_demo.manager;

import com.fatih.redis_cache_spring_boot_demo.dao.CategoryDao;
import com.fatih.redis_cache_spring_boot_demo.entity.CategoryEntity;
import com.fatih.redis_cache_spring_boot_demo.manager.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Service class for managing categories.
 * Implements {@link CategoryService} to handle category operations.
 */
@Service
@RequiredArgsConstructor
public class CategoryManager implements CategoryService {

    private final CategoryDao categoryDao;

    /**
     * Creates a new category.
     *
     * @param requestedCategory the category to be created
     * @return the created category entity
     */
    @Transactional
    @Override
    public CategoryEntity createCategory(CategoryEntity requestedCategory) {

        //Already existed check
        checkCategoryAlreadyExists(requestedCategory.getCategoryName());

        return categoryDao.save(requestedCategory);
    }

    /**
     * Updates an existing category.
     *
     * @param requestedCategory the category data to update
     * @return the updated category entity
     */
    @Transactional
    @Override
    public CategoryEntity updateCategory(CategoryEntity requestedCategory) {

        CategoryEntity foundCategory = getCategoryById(requestedCategory.getId());

        return categoryDao.save(checkUpdateConditions(requestedCategory, foundCategory));
    }

    /**
     * Deletes a category by its ID.
     *
     * @param id the category ID
     */
    @Transactional
    @Override
    public void deleteCategory(UUID id) {
        categoryDao.delete(getCategoryById(id));
    }

    /**
     * Retrieves a category by its ID.
     *
     * @param id the category ID
     * @return the found category entity
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public CategoryEntity getCategoryById(UUID id) {

        return categoryDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    /**
     * Retrieves all categories with pagination support.
     *
     * @param pageable pagination information
     * @return a paginated list of categories
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<CategoryEntity> getAllCategories(Pageable pageable) {
        return categoryDao.findAll(pageable);
    }

    /**
     * Checks if a category with the given name already exists.
     *
     * @param categoryName the name of the category
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected void checkCategoryAlreadyExists(String categoryName) {
        categoryDao.findByCategoryName(categoryName).ifPresent(categoryEntity -> {
            throw new RuntimeException("Category already exists!");
        });
    }

    /**
     * Checks update conditions and applies changes to the existing category.
     *
     * @param requestedCategory the requested category data
     * @param foundCategory     the existing category entity
     * @return the updated category entity
     */
    @Transactional
    protected CategoryEntity checkUpdateConditions(CategoryEntity requestedCategory, CategoryEntity foundCategory) {

        if (requestedCategory.getCategoryName() != null) {
            foundCategory.setCategoryName(requestedCategory.getCategoryName());
        }
        if (requestedCategory.getCategoryDescription() != null) {
            foundCategory.setCategoryDescription(requestedCategory.getCategoryDescription());
        }

        return foundCategory;
    }
}