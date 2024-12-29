package com.app.todolist_be.services;

import com.app.todolist_be.constants.AppConstant;
import com.app.todolist_be.dtos.GeneralResponse;
import com.app.todolist_be.dtos.UserDetailDto;
import com.app.todolist_be.entities.UserDetail;
import com.app.todolist_be.repositories.UserDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailService {

    private final UserDetailRepository userDetailRepository;
    @Override
    public GeneralResponse<?> login(UserDetailDto userDetailDto) {
        UserDetail existUserDetail = userDetailRepository.findByWaNumber(userDetailDto.getWaNumber());
        if (Objects.isNull(existUserDetail)) {
            UserDetail userDetail = UserDetail.builder()
                    .waNumber(userDetailDto.getWaNumber())
                    .build();
            userDetailRepository.save(userDetail);
        }

        return GeneralResponse.builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage(AppConstant.Response.SUCCESS_MESSAGE)
                .build();
    }
}
