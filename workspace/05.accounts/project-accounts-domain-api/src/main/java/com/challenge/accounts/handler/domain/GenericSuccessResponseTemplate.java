package com.challenge.accounts.handler.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record GenericSuccessResponseTemplate(
        @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
        LocalDateTime timestamp,
        String ticket,
        String status,
        Object message) {

        public GenericSuccessResponseTemplate(HttpStatus httpStatus, Object message) {
                this(
                        LocalDateTime.now(),
                        String.valueOf((int)(Math.random() * 999999999 + 1)),
                        httpStatus.toString(),
                        message
                );
        }
}
