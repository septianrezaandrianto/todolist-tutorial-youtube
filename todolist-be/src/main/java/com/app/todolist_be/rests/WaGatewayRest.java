package com.app.todolist_be.rests;

import com.app.todolist_be.dtos.GeneralResponse;
import com.app.todolist_be.dtos.WaGatewayRequest;

public interface WaGatewayRest {

    GeneralResponse<?> sendMessage(WaGatewayRequest waGatewayRequest);
}
