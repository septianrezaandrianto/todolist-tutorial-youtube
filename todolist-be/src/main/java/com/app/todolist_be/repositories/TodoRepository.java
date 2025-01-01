package com.app.todolist_be.repositories;

import com.app.todolist_be.entities.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TodoRepository extends MongoRepository<Todo, String> {

    @Query("${startDate : ?0, endDate : ?1}")
    List<Todo> findByDate(Date startDate, Date endDate);
}
