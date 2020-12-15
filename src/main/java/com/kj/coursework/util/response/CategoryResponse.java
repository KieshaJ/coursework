package com.kj.coursework.util.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryResponse {
    private String id;
    private UserResponse owner;
    private List<UserResponse> userList;
    private List<CategoryResponse> categoryList;

    private String name;
    private String companyId;
    private String parentId;

    private Double expenses;
    private Double earnings;
    private Double balance;

    public CategoryResponse(String id, UserResponse owner, List<UserResponse> userList, List<CategoryResponse> categoryList,
                            String name, String companyId, String parentId,
                            Double expenses, Double earnings, Double balance) {
        this.id = id;
        this.owner = owner;
        this.userList = userList;
        this.categoryList = categoryList;
        this.name = name;
        this.companyId = companyId;
        this.parentId = parentId;
        this.expenses = expenses;
        this.earnings = earnings;
        this.balance = balance;
    }
}
