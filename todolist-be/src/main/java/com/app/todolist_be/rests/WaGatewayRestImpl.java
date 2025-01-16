package com.app.todolist_be.rests;

import com.app.todolist_be.constants.AppConstant;
import com.app.todolist_be.dtos.GeneralResponse;
import com.app.todolist_be.dtos.WaGatewayRequest;
import com.app.todolist_be.utils.SslUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class WaGatewayRestImpl implements WaGatewayRest {

    @Value("${wa.token}")
    private String waToken;
    @Value("${wa.url}")
    private String waUrl;

    private final RestTemplate restTemplate;

    @Override
    public GeneralResponse<?> sendMessage(WaGatewayRequest waGatewayRequest) {
        SslUtil.disableSslVerification();
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, waToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<WaGatewayRequest> request = new HttpEntity<>(waGatewayRequest, headers);

        restTemplate.exchange(
                waUrl,
                HttpMethod.POST,
                request,
                String.class
        );
        return GeneralResponse.builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage(AppConstant.Response.SUCCESS_MESSAGE)
                .build();
    }
}
