package ru.kashtanov.just_fire_service.service;

import ru.kashtanov.just_fire_service.dto.GratitudeDto;
import ru.kashtanov.just_fire_service.dto.GratitudeSaveDto;
import ru.kashtanov.just_fire_service.model.Gratitude;

import java.util.List;
import java.util.Optional;

/**
 * @author Viktor Кashtanov
 */

public interface GratitudeService {


    public abstract GratitudeDto createGratitude(GratitudeSaveDto dto);

    public abstract Optional<Gratitude> findGratitudeInRepoById(Long id);

    public abstract GratitudeDto convertToDto(Gratitude gratitude);

    public abstract Gratitude saveGratitude(GratitudeSaveDto dto);

    public List<GratitudeDto> findAllGratitudes(int limit, int offset);

    public abstract List<GratitudeDto> findReceivedGratitudesByUser(Long userId);

    public abstract List<GratitudeDto> findSentGratitudesByUser(Long userId);

    public abstract Optional<GratitudeDto> findGratitudeById(Long id);
}
