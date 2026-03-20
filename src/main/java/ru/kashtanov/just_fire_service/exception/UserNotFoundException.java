package ru.kashtanov.just_fire_service.exception;

/**
 * @author Viktor Кashtanov
 */
public class UserNotFoundException extends RuntimeException {

   public UserNotFoundException(String message) {
       super(message);
   }
}

