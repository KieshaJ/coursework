package com.kj.coursework.service;

import com.kj.coursework.util.request.CategoryRequest;
import com.kj.coursework.util.response.CategoryResponse;
import org.bson.types.ObjectId;

public interface CategoryService {
    CategoryResponse get(ObjectId categoryId) throws Exception;
    CategoryResponse save(CategoryRequest category);
    CategoryResponse update(ObjectId categoryId, CategoryRequest category);
    Boolean delete(ObjectId categoryId);
}
