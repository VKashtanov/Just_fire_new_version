package ru.kashtanov.just_fire_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kashtanov.just_fire_service.model.join.GratitudeRecipient;

/**
 * @author Viktor Кashtanov
 */
@Repository
public interface GratitudeRecipientRepo extends JpaRepository<GratitudeRecipient, Long> {
}
