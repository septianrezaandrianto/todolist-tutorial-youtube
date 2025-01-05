package com.app.todolist_be.services;

import com.app.todolist_be.constants.AppConstant;
import com.app.todolist_be.dtos.GeneralResponse;
import com.app.todolist_be.dtos.PaginationRequest;
import com.app.todolist_be.dtos.TodoDto;
import com.app.todolist_be.entities.Todo;
import com.app.todolist_be.handlers.DataExistException;
import com.app.todolist_be.repositories.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    @Override
    @Transactional
    public GeneralResponse<?> add(TodoDto todoDto) {
        log.info("todoDto" + todoDto);
        Date startDate = constructDate(todoDto.getStartTime());
        Date endDate = constructDate(todoDto.getEndTime());
        List<Todo> todoList = todoRepository.findByDate(startDate, endDate, todoDto.getWaNumber());

        if(!todoList.isEmpty()) {
            throw new DataExistException("Pada periode waktu ini, anda sudah mempunyai jadwal aktifitas");
        }
        Date now = Date.from(LocalDateTime.now().atZone(ZoneId.of("UTC")).toInstant());
        Todo todo = Todo.builder()
                .startDate(startDate)
                .endDate(endDate)
                .priority(todoDto.getPriority())
                .title(todoDto.getTitle())
                .status(AppConstant.Status.CREATED)
                .waNumber(todoDto.getWaNumber())
                .createdDate(now)
                .build();
        Todo savedTodo = todoRepository.save(todo);

        return GeneralResponse.builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage(AppConstant.Response.SUCCESS_MESSAGE)
                .data(savedTodo)
                .build();

    }

    @Override
    public GeneralResponse<?> getDataPaging(PaginationRequest paginationRequest) {
        Sort sort = Sort.by(Sort.Order.asc("startDate"));
        Pageable pageable = PageRequest.of(paginationRequest.getPageNumber(), paginationRequest.getPageSize(), sort);
        Page<Todo> pageResult = todoRepository.findByWaNumber(paginationRequest.getWaNumber(), pageable);
        return GeneralResponse.builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage(AppConstant.Response.SUCCESS_MESSAGE)
                .data(pageResult.getContent())
                .totalPage(pageResult.getTotalPages())
                .totalData(pageResult.getTotalElements())
                .build();
    }

    //HH:mm
    private Date constructDate(String time) {
        LocalDateTime now = LocalDateTime.now();
        String[] timeParts = time.split(":");
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);
        LocalDateTime updateDateTime = now.withHour(hour)
                .withMinute(minute)
                .withSecond(0)
                .withNano(0);
        return Date.from(updateDateTime.atZone(ZoneId.of("UTC")).toInstant());
    }
}
