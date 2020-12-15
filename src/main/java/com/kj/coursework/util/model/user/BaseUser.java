package com.kj.coursework.util.model.user;

import com.kj.coursework.util.enums.UserType;
import com.kj.coursework.util.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
public abstract class BaseUser extends BaseEntity {
    private UserType userType;
    private String username;
    private String password;

    public BaseUser(ObjectId id, UserType userType, String username, String password) {
        super(id);
        this.userType = userType;
        this.username = username;
        this.password = password;
    }
}
