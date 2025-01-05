package com.app.todolist_be.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationRequest {

    private String waNumber;
    private Integer pageNumber;
    private Integer pageSize;

}
