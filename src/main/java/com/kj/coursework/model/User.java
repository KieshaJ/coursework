package com.kj.coursework.model;

import com.kj.coursework.util.enums.UserType;
import com.kj.coursework.util.model.user.BaseUser;
import com.kj.coursework.util.model.user.UserInfo;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Getter
@Setter
public class User extends BaseUser {
    private ObjectId companyId;
    private ObjectId categoryId;
    private UserInfo userInfo;

    public User(ObjectId id, UserType userType, String username, String password, ObjectId companyId, ObjectId categoryId, UserInfo userInfo) {
        super(id, userType, username, password);
        this.companyId = companyId;
        this.categoryId = categoryId;
        this.userInfo = userInfo;
    }
}
