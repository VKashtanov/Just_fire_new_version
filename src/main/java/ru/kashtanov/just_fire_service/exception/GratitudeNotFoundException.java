package ru.kashtanov.just_fire_service.exception;

import lombok.Getter;

/**
 * @author Viktor Кashtanov
 */
@Getter
public class GratitudeNotFoundException extends RuntimeException {

    public GratitudeNotFoundException(String message) {
        super(message);
    }
}

