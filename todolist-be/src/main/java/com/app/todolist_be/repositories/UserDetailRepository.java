package com.app.todolist_be.repositories;

import com.app.todolist_be.entities.UserDetail;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailRepository extends MongoRepository<UserDetail, String> {

    @Query("{waNumber : ?0}")
    UserDetail findByWaNumber(String waNumber);
}
