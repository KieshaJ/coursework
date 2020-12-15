package com.kj.coursework.util.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class CategoryRequest {
    private String id;
    private String ownerId;
    private List<String> userIdList;
    private List<String> categoryIdList;

    private String name;
    private String companyId;
    private String parentId;

    private Double expenses;
    private Double earnings;
}
