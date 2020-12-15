package com.kj.coursework.util.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserInfo {
    private String name;
    private String surname;
    private String phoneNo;
    private String email;
    private String address;
}
