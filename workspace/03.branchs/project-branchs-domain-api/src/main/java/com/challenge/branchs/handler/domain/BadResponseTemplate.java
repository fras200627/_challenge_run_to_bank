package com.challenge.branchs.handler.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record BadResponseTemplate(
        @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
        LocalDateTime timestamp,
        String ticket,
        String status,
        Object error,
        Object message) {

        public BadResponseTemplate(HttpStatus httpStatus, Object error, Object message) {
                this(
                        LocalDateTime.now(),
                        String.valueOf((int)(Math.random() * 999999999 + 1)),
                        httpStatus.toString(),
                        error,
                        message
                );
        }

}
