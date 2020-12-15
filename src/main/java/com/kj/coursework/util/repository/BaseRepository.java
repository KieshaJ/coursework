package com.kj.coursework.util.repository;

import com.kj.coursework.util.model.BaseEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BaseRepository<T extends BaseEntity> extends MongoRepository<T, ObjectId> {
}
