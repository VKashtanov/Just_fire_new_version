package ru.kashtanov.just_fire_service.service.impl;

import org.springframework.stereotype.Service;
import ru.kashtanov.just_fire_service.model.join.GratitudeRecipient;
import ru.kashtanov.just_fire_service.repository.GratitudeRecipientRepo;
import ru.kashtanov.just_fire_service.repository.GratitudeRepo;

/**
 * @author Viktor Кashtanov
 */
@Service
public class GratitudeRecipientService {
    private final GratitudeRecipientRepo gratitudeRecipientRepo;

    public GratitudeRecipientService(GratitudeRecipientRepo gratitudeRecipientRepo) {
        this.gratitudeRecipientRepo = gratitudeRecipientRepo;
    }


    public GratitudeRecipient createGratitudeRecipient(GratitudeRecipient gratitudeRecipient) {
        return gratitudeRecipientRepo.save(gratitudeRecipient);
    }
}
