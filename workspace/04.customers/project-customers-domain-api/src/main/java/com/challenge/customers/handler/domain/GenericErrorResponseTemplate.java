package com.challenge.customers.handler.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record GenericErrorResponseTemplate(
        @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
        LocalDateTime timestamp,
        String ticket,
        Object status,
        Object error,
        Object message) {

        public GenericErrorResponseTemplate(HttpStatus httpStatus, Object error, Object message) {
                this(
                        LocalDateTime.now(),
                        String.valueOf((int)(Math.random() * 999999999 + 1)),
                        httpStatus.value(),
                        error,
                        message
                );
        }
}
