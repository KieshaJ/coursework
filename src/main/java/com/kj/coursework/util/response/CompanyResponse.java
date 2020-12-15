package com.kj.coursework.util.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CompanyResponse {
    private String id;
    private String name;
    private UserResponse owner;
    private List<UserResponse> userList;
    private List<CategoryResponse> categoryList;
    private Double expenses;
    private Double earnings;
    private Double balance;

    public CompanyResponse(String id, String name, UserResponse owner, List<UserResponse> userList, List<CategoryResponse> categoryList, Double expenses, Double earnings) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.userList = userList;
        this.categoryList = categoryList;
        this.expenses = expenses;
        this.earnings = earnings;
        this.balance = earnings - expenses;
    }
}
