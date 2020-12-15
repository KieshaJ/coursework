package com.kj.coursework.model;

import com.kj.coursework.util.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "categories")
@Getter
@Setter
public class Category extends BaseEntity {
    @DBRef
    private User owner;
    @DBRef
    private List<User> userList;
    @DBRef
    private List<Category> categoryList;

    private String name;
    private ObjectId companyId;
    private ObjectId parentId;

    private Double expenses;
    private Double earnings;

    public Category(ObjectId id, User owner, List<User> userList, List<Category> categoryList,
                    String name, ObjectId companyId, ObjectId parentId,
                    Double expenses, Double earnings) {
        super(id != null ? id : new ObjectId());
        this.owner = owner;
        this.userList = userList;
        this.categoryList = categoryList;

        this.name = name;
        this.companyId = companyId;
        this.parentId = parentId;

        this.expenses = expenses + sumListExpenses();
        this.earnings = earnings + sumListEarnings();
    }

    private Double sumListExpenses() {
        if(categoryList != null && categoryList.size() > 0) {
            return categoryList.stream().mapToDouble(Category::getExpenses).sum();
        }

        return 0.0;
    }

    private Double sumListEarnings() {
        if(categoryList != null && categoryList.size() > 0) {
            return categoryList.stream().mapToDouble(Category::getEarnings).sum();
        }

        return 0.0;
    }
}
