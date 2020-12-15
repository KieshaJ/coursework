package com.kj.coursework.service;

import com.kj.coursework.util.request.LoginRequest;
import com.kj.coursework.util.request.UserRequest;
import com.kj.coursework.util.response.UserResponse;
import org.bson.types.ObjectId;

public interface UserService {
    UserResponse login(LoginRequest loginRequest) throws Exception;
    UserResponse get(ObjectId userId) throws Exception;
    UserResponse save(UserRequest userRequest) throws Exception;
    UserResponse update(ObjectId userId, UserRequest userRequest) throws Exception;
    Boolean delete(ObjectId userId);
}
