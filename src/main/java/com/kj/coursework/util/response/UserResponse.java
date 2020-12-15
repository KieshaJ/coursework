package com.kj.coursework.util.response;

import com.kj.coursework.util.enums.UserType;
import com.kj.coursework.util.model.user.UserInfo;

public class UserResponse {
    private String id;
    private String companyId;
    private String categoryId;
    private UserType userType;
    private String username;
    private UserInfo userInfo;

    public UserResponse(String id, String companyId, String categoryId, UserType userType, String username, UserInfo userInfo) {
        this.id = id;
        this.companyId = companyId;
        this.categoryId = categoryId;
        this.userType = userType;
        this.username = username;
        this.userInfo = userInfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
