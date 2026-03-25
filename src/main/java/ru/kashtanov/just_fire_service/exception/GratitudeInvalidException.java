package ru.kashtanov.just_fire_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author Viktor Кashtanov
 */
@Getter
public class GratitudeInvalidException extends RuntimeException {
    private final HttpStatus status;

    public GratitudeInvalidException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

}
