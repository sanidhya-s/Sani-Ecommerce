package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private List<Category> categories = new ArrayList<>();
    private long nextId = 1;

    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public void createCategory(Category category) {
        category.setCategoryId(nextId++);
        categories.add(category);
    }

    @Override
    public String deleteCategory(long categoryId) {
        for (Category category : categories) {
            if (category.getCategoryId() == categoryId) {
                categories.remove(category);
                return "Deleted";
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
    }

    @Override
    public String updateCategory(Category category, Long categoryId) {
        for (Category category1 : categories) {
            if (category1.getCategoryId() == categoryId) {
                category1.setCategoryName(category.getCategoryName());
                return "Updated";
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
    }
}
