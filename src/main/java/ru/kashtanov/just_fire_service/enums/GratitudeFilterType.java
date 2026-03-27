package ru.kashtanov.just_fire_service.enums;

import lombok.Getter;

/**
 * @author Viktor Кashtanov
 */

@Getter
public enum GratitudeFilterType {
    ALL("all"),
    GD("gd"),
    RECEIVED("received"),
    SENT("sent");

    private final String type;
    GratitudeFilterType(String type) {
        this.type = type;
    }

}
