package com.kj.coursework.util.service;

import com.kj.coursework.model.Company;
import com.kj.coursework.model.User;
import com.kj.coursework.repository.CompanyRepository;
import com.kj.coursework.repository.UserRepository;
import com.kj.coursework.util.enums.UserType;
import com.kj.coursework.util.request.UserRequest;
import com.kj.coursework.util.response.UserResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserServiceUtils {
    public static Company createCompany(String companyName, CompanyRepository companyRepository) {
        Company newCompany = new Company(
                null,
                null,
                null,
                null,
                companyName
        );

        return companyRepository.insert(newCompany);
    }

    public static User createIndividualUser(UserRequest userRequest) {
        return new User(
                null,
                userRequest.getUserType(),
                userRequest.getUsername(),
                userRequest.getPassword(),
                userRequest.getCompanyId(),
                userRequest.getCategoryId(),
                userRequest.getUserInfo()
        );
    }

    public static User createCompanyUser(Company company, UserRequest userRequest) {
        return new User(
                null,
                userRequest.getUserType(),
                userRequest.getUsername(),
                userRequest.getPassword(),
                company.getId(),
                userRequest.getCategoryId(),
                userRequest.getUserInfo()
        );
    }

    public static void updateEntityFields(User user, UserRequest userRequest) {
        user.setUserInfo(userRequest.getUserInfo());
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        user.setCompanyId(userRequest.getCompanyId());
        user.setCategoryId(userRequest.getCategoryId());
    }

    public static UserResponse entityToResponse(User user) {
        return new UserResponse(
                user.getId().toString(),
                user.getCompanyId() != null ? user.getCompanyId().toString() : null,
                user.getCategoryId() != null ? user.getCategoryId().toString() : null,
                user.getUserType(),
                user.getUsername(),
                user.getUserInfo()
        );
    }

    public static List<UserResponse> entityListToResponseList(List<User> userList) {
        if(userList  != null) {
            return userList.stream().map(UserServiceUtils::entityToResponse).collect(Collectors.toList());
        }
        else {
            return new ArrayList<>();
        }
    }

    public static void validateRequest(UserRequest userRequest, UserRepository repository) throws Exception {
        if(
                userRequest.getUserType() == null ||
                userRequest.getUsername() == null ||
                userRequest.getPassword() == null ||
                userRequest.getUserInfo() == null ||
                userRequest.getUserInfo().getName() == null ||
                userRequest.getUserInfo().getSurname() == null ||
                userRequest.getUserInfo().getPhoneNo() == null ||
                userRequest.getUserInfo().getEmail() == null ||
                userRequest.getUserInfo().getAddress() == null
        ) {
            throw new Exception("Necessary fields not set");
        }

        if(userRequest.getUserType().equals(UserType.COMPANY)) {
            if(userRequest.getCompanyName() == null) {
                throw new Exception("Company name not set");
            }
        }

        User test = repository.findByUsername(userRequest.getUsername());
        if(test != null) {
            throw new Exception("Username taken");
        }
    }
}
