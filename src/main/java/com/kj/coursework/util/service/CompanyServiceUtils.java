package com.kj.coursework.util.service;

import com.kj.coursework.model.Category;
import com.kj.coursework.model.Company;
import com.kj.coursework.model.User;
import com.kj.coursework.repository.CategoryRepository;
import com.kj.coursework.repository.UserRepository;
import com.kj.coursework.util.request.CompanyRequest;
import com.kj.coursework.util.response.CompanyResponse;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class CompanyServiceUtils {
    public static CompanyResponse entityToResponse(Company company) {
        return new CompanyResponse(
                company.getId().toString(),
                company.getName(),
                UserServiceUtils.entityToResponse(company.getOwner()),
                UserServiceUtils.entityListToResponseList(company.getUserList()),
                CategoryServiceUtils.entityListToResponseList(company.getCategoryList()),
                company.getExpenses(),
                company.getEarnings()
        );
    }

    public static Company updateEntityFields(Company company, CompanyRequest companyRequest, UserRepository userRepository, CategoryRepository categoryRepository) {
        company.setName(companyRequest.getName());

        List<User> userList = new ArrayList<>();
        if(companyRequest.getUserIdList() != null) {
            companyRequest.getUserIdList().forEach(id -> {
                userRepository.findById(new ObjectId(id)).ifPresent(userList::add);
            });
        }

        List<Category> categoryList = new ArrayList<>();
        if(companyRequest.getCategoryIdList() != null) {
            companyRequest.getUserIdList().forEach(id -> {
                categoryRepository.findById(new ObjectId(id)).ifPresent(categoryList::add);
            });
        }

        company.setUserList(userList);
        company.setCategoryList(categoryList);
        return company;
    }
}
