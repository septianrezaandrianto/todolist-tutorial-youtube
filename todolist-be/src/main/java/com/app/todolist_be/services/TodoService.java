package com.app.todolist_be.services;

import com.app.todolist_be.dtos.GeneralResponse;
import com.app.todolist_be.dtos.TodoDto;

public interface TodoService {

    GeneralResponse<?> add(TodoDto todoDto);
}