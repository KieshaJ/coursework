package com.kj.coursework.repository;

import com.kj.coursework.model.User;
import com.kj.coursework.util.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User> {
    User findByUsername(String username);
}
