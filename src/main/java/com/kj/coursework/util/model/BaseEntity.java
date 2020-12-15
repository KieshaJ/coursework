package com.kj.coursework.util.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@Getter
@Setter
public abstract class BaseEntity {
    @Id
    private ObjectId id;
}
