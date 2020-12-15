package com.kj.coursework.util.request;

import com.kj.coursework.util.enums.UserType;
import com.kj.coursework.util.model.user.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@AllArgsConstructor
@Getter
@Setter
public class UserRequest {
    private UserInfo userInfo;
    private String username;
    private String password;
    private UserType userType;
    private String companyName;
    private ObjectId companyId;
    private ObjectId categoryId;
}
