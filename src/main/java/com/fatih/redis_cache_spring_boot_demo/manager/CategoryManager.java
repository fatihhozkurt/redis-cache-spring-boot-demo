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

@Service
@RequiredArgsConstructor
public class CategoryManager implements CategoryService {

    private final CategoryDao categoryDao;

    @Transactional
    @Override
    public CategoryEntity createCategory(CategoryEntity requestedCategory) {

        //Already existed check
        checkCategoryAlreadyExists(requestedCategory.getCategoryName());

        return categoryDao.save(requestedCategory);
    }

    @Transactional
    @Override
    public CategoryEntity updateCategory(CategoryEntity requestedCategory) {

        CategoryEntity foundCategory = getCategoryById(requestedCategory.getId());

        return categoryDao.save(checkUpdateConditions(requestedCategory, foundCategory));
    }

    @Transactional
    @Override
    public void deleteCategory(UUID id) {
        categoryDao.delete(getCategoryById(id));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public CategoryEntity getCategoryById(UUID id) {

        return categoryDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<CategoryEntity> getAllCategories(Pageable pageable) {
        return categoryDao.findAll(pageable);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected void checkCategoryAlreadyExists(String categoryName) {
        categoryDao.findByCategoryName(categoryName).ifPresent(categoryEntity -> {
            throw new RuntimeException("Category already exists!");
        });
    }

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