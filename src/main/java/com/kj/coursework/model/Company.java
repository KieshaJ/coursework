package com.kj.coursework.model;

import com.kj.coursework.util.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "companies")
@Getter
@Setter
public class Company extends BaseEntity {
    @DBRef
    private User owner;
    @DBRef
    private List<User> userList;
    @DBRef
    private List<Category> categoryList;

    private String name;

    private Double expenses;
    private Double earnings;
    private Double balance;

    public Company(ObjectId id, User owner, List<User> userList, List<Category> categoryList, String name) {
        super(id);
        this.owner = owner;
        this.userList = userList;
        this.categoryList = categoryList;
        this.name = name;

        this.expenses = sumCategoryExpenses();
        this.earnings = sumCategoryEarnings();
        this.balance = earnings - expenses;
    }

    private Double sumCategoryExpenses() {
        if(categoryList != null && categoryList.size() > 0) {
            return categoryList.stream().mapToDouble(Category::getExpenses).sum();
        }

        return 0.0;
    }

    private Double sumCategoryEarnings() {
        if(categoryList != null && categoryList.size() > 0) {
            return categoryList.stream().mapToDouble(Category::getEarnings).sum();
        }

        return 0.0;
    }
}
