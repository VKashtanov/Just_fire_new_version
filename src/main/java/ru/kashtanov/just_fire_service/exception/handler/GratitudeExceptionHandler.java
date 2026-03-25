package ru.kashtanov.just_fire_service.exception.handler;

import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kashtanov.just_fire_service.dto.response.ErrorResponse;
import ru.kashtanov.just_fire_service.exception.GratitudeInvalidException;

import java.time.LocalDateTime;

import static ru.kashtanov.just_fire_service.exception.constants.HandlerOrder.GRATITUDE_HANDLER_ORDER;

/**
 * @author Viktor Кashtanov
 */
@RestControllerAdvice
@Order(GRATITUDE_HANDLER_ORDER)
public class GratitudeExceptionHandler {

    @ExceptionHandler(GratitudeInvalidException.class)
    public ResponseEntity<ErrorResponse> handleInvalidGratitude(GratitudeInvalidException e) {
        // 400 Bad Request
        return ResponseEntity
                .status(404)
                .body(ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(404)
                        .error("Not Found")
                        .message(e.getMessage())
                        .build());
        }
}
