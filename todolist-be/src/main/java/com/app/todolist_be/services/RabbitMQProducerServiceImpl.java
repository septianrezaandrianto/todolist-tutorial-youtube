package com.app.todolist_be.services;

import com.app.todolist_be.constants.AppConstant;
import com.app.todolist_be.dtos.Msg;
import com.app.todolist_be.entities.Todo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitMQProducerServiceImpl implements  RabbitMQProducerService {

    private final RabbitTemplate rabbitTemplate;

    @Override
    @SneakyThrows
    @Async
    public void sendMessageDelay(Todo todo) {
        ObjectMapper objectMapper = new ObjectMapper();
        LocalDateTime startTime = convertTime(todo.getStartDate());
        LocalDateTime endTIme = convertTime(todo.getEndDate());
        Long differentTime = Duration.between(startTime, endTIme).toMinutes();

        Long scheduleTIme = AppConstant.RabbitMQ.SCHEDULE_FOURTH_MINUTE;
        if(isValidTime(differentTime, AppConstant.RabbitMQ.SCHEDULE_FOURTH_MINUTE)) {
            scheduleTIme = AppConstant.RabbitMQ.SCHEDULE_THIRD_MINUTE;
        } else if (isValidTime(differentTime, AppConstant.RabbitMQ.SCHEDULE_THIRD_MINUTE)) {
            scheduleTIme = AppConstant.RabbitMQ.SCHEDULE_SECOND_MINUTE;
        } else if(isValidTime(differentTime, AppConstant.RabbitMQ.SCHEDULE_SECOND_MINUTE)) {
            scheduleTIme = AppConstant.RabbitMQ.SCHEDULE_FIRST_MINUTE;
        }

        Long totalDifferentTime = differentTime - scheduleTIme;
        Map<String, List<Long>> delayConfig = Map.of(
            AppConstant.Priority.HIGH , List.of(
                AppConstant.RabbitMQ.SCHEDULE_FIRST_MINUTE,
                AppConstant.RabbitMQ.SCHEDULE_SECOND_MINUTE,
                AppConstant.RabbitMQ.SCHEDULE_THIRD_MINUTE,
                AppConstant.RabbitMQ.SCHEDULE_FOURTH_MINUTE
            ),
            AppConstant.Priority.MEDIUM, List.of(
                AppConstant.RabbitMQ.SCHEDULE_SECOND_MINUTE,
                AppConstant.RabbitMQ.SCHEDULE_THIRD_MINUTE,
                AppConstant.RabbitMQ.SCHEDULE_FOURTH_MINUTE
            ),
            AppConstant.Priority.LOW, List.of(
                AppConstant.RabbitMQ.SCHEDULE_THIRD_MINUTE,
                AppConstant.RabbitMQ.SCHEDULE_FOURTH_MINUTE
            )
        );
        List<Long> delays = delayConfig.get(todo.getPriority());
        String finishTime = String.valueOf(endTIme).split("T")[1];
        Msg msg = new Msg();

        List<Long> countMinutesList = IntStream.range(0, delays.size())
                .map(j -> delays.size() - 1 - j)
                .mapToObj(delays::get)
                .collect(Collectors.toList());

        for (int i =0 ; i < delays.size() ; i++) {
            msg.setId(todo.getId());
            msg.setWaNumber(todo.getWaNumber());

            if ( i != delays.size() -1) {
                LocalDateTime nowTime = endTIme.minusMinutes(countMinutesList.get(i));
                String now = String.valueOf(nowTime).split("T")[1];
                msg.setMessage("Hai, [".concat(todo.getWaNumber()).concat("] saat ini pukul ").concat(now)
                        .concat(" waktu anda akan berakhir pada ").concat(finishTime).concat(" anda mempunyai waktu ")
                        .concat(String.valueOf(countMinutesList.get(i))).concat(" menit lagi, sebelum aktifitas *")
                        .concat(todo.getTitle()).concat("* berakhir..."));
                msg.setRemark(AppConstant.Status.CREATED);
            } else {
                msg.setMessage("Hai, [".concat(todo.getWaNumber()).concat("] selamat anda telah menyelesaikan aktifitas *")
                        .concat(todo.getTitle()).concat("* dengan baik..."));
                msg.setRemark(AppConstant.Status.DONE);
            }
            String convertMsg = objectMapper.writeValueAsString(msg);
            Long totalDelay = totalDifferentTime + delays.get(i);
            log.info("totalDelay " + todo.getTitle() + " >> " + totalDelay);
            sendMessage(totalDelay, convertMsg);
        }
    }

    private void sendMessage(long delayTime, String message) {
        long delay = delayTime * 60 * 1000;
        try {
            Message delayedMessage = MessageBuilder.withBody(message.getBytes())
                    .setHeader("x-delay", (int)delay)
                    .build();
            rabbitTemplate.send(AppConstant.RabbitMQ.EXCHANGE_NAME,
                    AppConstant.RabbitMQ.QUEUE_NAME, delayedMessage);
        } catch (Exception e) {
            log.error("Error send message to queue " + e);
        }
    }
    private LocalDateTime convertTime(Date date) {
        return date.toInstant()
                .atZone(ZoneId.of("UTC")).toLocalDateTime();
    }

    private boolean isValidTime(Long totalTime, Long actualTime) {
        return totalTime < actualTime;
    }
}
