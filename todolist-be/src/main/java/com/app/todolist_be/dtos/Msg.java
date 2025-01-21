package com.app.todolist_be.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Msg {

    private String id;
    private String message;
    private String remark;
    private String waNumber;
}
