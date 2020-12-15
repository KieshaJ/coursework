package com.kj.coursework.service.impl;

import com.kj.coursework.model.Category;
import com.kj.coursework.model.Company;
import com.kj.coursework.repository.CategoryRepository;
import com.kj.coursework.repository.CompanyRepository;
import com.kj.coursework.repository.UserRepository;
import com.kj.coursework.service.CategoryService;
import com.kj.coursework.util.request.CategoryRequest;
import com.kj.coursework.util.response.CategoryResponse;
import com.kj.coursework.util.service.CategoryServiceUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public CategoryResponse get(ObjectId categoryId) throws Exception {
        Category category = repository.findById(categoryId).orElse(null);

        if(category == null) {
            throw new Exception("Category not found");
        }

        return CategoryServiceUtils.entityToResponse(category);
    }

    @Override
    public CategoryResponse save(CategoryRequest categoryRequest) {
        Category category = CategoryServiceUtils.requestToEntity(categoryRequest, repository, userRepository, companyRepository);
        category = repository.save(category);

        if(category.getCompanyId() != null) {
            Company company = companyRepository.findById(category.getCompanyId()).orElse(null);
            if(company != null) {
                if(company.getCategoryList() != null) {
                    company.getCategoryList().add(category);
                }
                else {
                    company.setCategoryList(Collections.singletonList(category));
                }
                companyRepository.save(company);
            }
        }

        if(category.getParentId() != null) {
            Category parentCategory = repository.findById(category.getParentId()).orElse(null);
            if(parentCategory != null) {
                parentCategory.getCategoryList().add(category);
                repository.save(parentCategory);
            }
        }

        return CategoryServiceUtils.entityToResponse(category);
    }

    @Override
    public CategoryResponse update(ObjectId categoryId, CategoryRequest category) {
        return null;
    }

    @Override
    public Boolean delete(ObjectId categoryId) {
        return null;
    }
}
