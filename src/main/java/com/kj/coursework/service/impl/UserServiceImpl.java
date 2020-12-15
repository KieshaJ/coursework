package com.kj.coursework.service.impl;

import com.kj.coursework.model.Company;
import com.kj.coursework.model.User;
import com.kj.coursework.repository.CompanyRepository;
import com.kj.coursework.repository.UserRepository;
import com.kj.coursework.service.UserService;
import com.kj.coursework.util.enums.UserType;
import com.kj.coursework.util.request.LoginRequest;
import com.kj.coursework.util.request.UserRequest;
import com.kj.coursework.util.response.UserResponse;
import com.kj.coursework.util.service.UserServiceUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public UserResponse login(LoginRequest loginRequest) throws Exception {
        User user = repository.findByUsername(loginRequest.getUsername());

        if(user == null) {
            throw new Exception("User not found");
        }

        if(!user.getUsername().equals(loginRequest.getUsername()) || !user.getPassword().equals(loginRequest.getPassword())) {
            throw new Exception("Invalid credentials");
        }

        return UserServiceUtils.entityToResponse(user);
    }

    @Override
    public UserResponse get(ObjectId userId) throws Exception {
        User user = repository.findById(userId).orElse(null);

        if(user == null) {
            throw new Exception("User not found");
        }

        return UserServiceUtils.entityToResponse(user);
    }

    @Override
    public UserResponse save(UserRequest userRequest) throws Exception {
        User user;
        UserServiceUtils.validateRequest(userRequest, repository);

        if(userRequest.getUserType().equals(UserType.COMPANY)) {
            Company company = UserServiceUtils.createCompany(userRequest.getCompanyName(), companyRepository);
            user = UserServiceUtils.createCompanyUser(company, userRequest);

            user = repository.save(user);
            company.setOwner(user);
            companyRepository.save(company);
        }
        else {
            user = UserServiceUtils.createIndividualUser(userRequest);
            user = repository.save(user);
        }

        return UserServiceUtils.entityToResponse(user);
    }

    @Override
    public UserResponse update(ObjectId userId, UserRequest userRequest) throws Exception {
        User user = repository.findById(userId).orElse(null);

        if(user == null) {
            throw new Exception("User not found");
        }

        UserServiceUtils.updateEntityFields(user, userRequest);
        user = repository.save(user);
        return UserServiceUtils.entityToResponse(user);
    }

    @Override
    public Boolean delete(ObjectId userId) {
        repository.deleteById(userId);
        return !repository.existsById(userId);
    }
}
