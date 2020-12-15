package com.kj.coursework.util.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class CompanyRequest {
    private String id;
    private List<String> userIdList;
    private List<String> categoryIdList;
    private String name;
    private CategoryRequest newCategory;
}
