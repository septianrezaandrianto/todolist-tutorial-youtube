package com.app.todolist_be.services;

import com.app.todolist_be.dtos.GeneralResponse;
import com.app.todolist_be.dtos.UserDetailDto;

public interface UserDetailService {

    GeneralResponse<?> login(UserDetailDto userDetailDto);
}
