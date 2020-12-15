package com.kj.coursework.util.service;

import com.kj.coursework.model.Category;
import com.kj.coursework.model.User;
import com.kj.coursework.repository.CategoryRepository;
import com.kj.coursework.repository.CompanyRepository;
import com.kj.coursework.repository.UserRepository;
import com.kj.coursework.util.request.CategoryRequest;
import com.kj.coursework.util.response.CategoryResponse;
import com.kj.coursework.util.response.UserResponse;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryServiceUtils {
    public static Category requestToEntity(CategoryRequest categoryRequest, CategoryRepository categoryRepository, UserRepository userRepository, CompanyRepository companyRepository) {
        ObjectId categoryId = null;
        if(categoryRequest.getId() != null) {
            categoryId = new ObjectId(categoryRequest.getId());
        }

        User owner = null;
        if(categoryRequest.getOwnerId() != null) {
            owner = userRepository.findById(new ObjectId(categoryRequest.getOwnerId())).orElse(null);
        }

        List<User> userList = new ArrayList<>();
        if(categoryRequest.getUserIdList() != null) {
            categoryRequest.getUserIdList().forEach(id -> userRepository.findById(new ObjectId(id)).ifPresent(userList::add));
        }

        List<Category> categoryList = new ArrayList<>();
        if(categoryRequest.getCategoryIdList() != null) {
            categoryRequest.getCategoryIdList().forEach(id -> categoryRepository.findById(new ObjectId(id)).ifPresent(categoryList::add));
        }

        ObjectId companyId = categoryRequest.getCompanyId() != null ? new ObjectId(categoryRequest.getCompanyId()) : null;
        ObjectId parentId = categoryRequest.getParentId() != null ? new ObjectId(categoryRequest.getParentId()) : null;

        return new Category(
                categoryId,
                owner,
                userList,
                categoryList,
                categoryRequest.getName(),
                companyId,
                parentId,
                categoryRequest.getExpenses(),
                categoryRequest.getEarnings()
        );
    }

    public static CategoryResponse entityToResponse(Category category) {
        UserResponse owner = null;
        if(category.getOwner() != null) {
            owner = UserServiceUtils.entityToResponse(category.getOwner());
        }

        List<UserResponse> userList = new ArrayList<>();
        if(category.getUserList() != null) {
            category.getUserList().forEach(user -> {
                userList.add(UserServiceUtils.entityToResponse(user));
            });
        }

        List<CategoryResponse> categoryList = new ArrayList<>();
        if(category.getCategoryList() != null) {
            category.getCategoryList().forEach(cat -> {
                categoryList.add(CategoryServiceUtils.entityToResponse(cat));
            });
        }

        String companyId = null;
        if(category.getCompanyId() != null) {
            companyId = category.getCompanyId().toString();
        }

        String parentId = null;
        if(category.getParentId() != null) {
            parentId = category.getParentId().toString();
        }

        return new CategoryResponse(
                category.getId().toString(),
                owner,
                userList,
                categoryList,
                category.getName(),
                companyId,
                parentId,
                category.getExpenses(),
                category.getEarnings(),
                category.getEarnings() - category.getExpenses()
        );
    }

    public static List<CategoryResponse> entityListToResponseList(List<Category> categoryList) {
        if(categoryList != null) {
            return categoryList.stream().map(CategoryServiceUtils::entityToResponse).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
