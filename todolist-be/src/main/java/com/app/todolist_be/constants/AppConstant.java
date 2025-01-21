package com.app.todolist_be.constants;

public interface AppConstant {

    class Response {
        public static final String SUCCESS_MESSAGE = "Successful";
    }

    class Status {
        public static final String CREATED = "Created";
        public static final String DONE = "Done";
    }

    class RabbitMQ {
        public static final String QUEUE_NAME = "whatsapp";
        public static final String EXCHANGE_NAME = "notification";
        public static final Long SCHEDULE_FIRST_MINUTE = 2L;
        public static final Long SCHEDULE_SECOND_MINUTE = 4L;
        public static final Long SCHEDULE_THIRD_MINUTE = 6L;
        public static final Long SCHEDULE_FOURTH_MINUTE = 8L;
    }

    class Priority {
        public static final String HIGH = "High";
        public static final String MEDIUM = "Medium";
        public static final String LOW = "Low";
    }
}
