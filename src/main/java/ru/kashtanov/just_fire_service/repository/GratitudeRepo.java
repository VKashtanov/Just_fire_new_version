package ru.kashtanov.just_fire_service.repository;

import org.springframework.stereotype.Repository;
import ru.kashtanov.just_fire_service.model.Gratitude;

/**
 * @author Viktor Кashtanov
 */
@Repository
public interface GratitudeRepo {
    public void save(Gratitude gratitude);
}
