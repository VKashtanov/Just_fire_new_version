package ru.kashtanov.just_fire_service.service.impl;

import org.springframework.stereotype.Service;
import ru.kashtanov.just_fire_service.dto.GratitudeDto;
import ru.kashtanov.just_fire_service.dto.GratitudeSaveDto;
import ru.kashtanov.just_fire_service.dto.UserDto;
import ru.kashtanov.just_fire_service.enums.GratitudeFilterType;
import ru.kashtanov.just_fire_service.model.Gratitude;
import ru.kashtanov.just_fire_service.model.User;
import ru.kashtanov.just_fire_service.model.join.GratitudeRecipient;
import ru.kashtanov.just_fire_service.repository.GratitudeRepo;
import ru.kashtanov.just_fire_service.service.GratitudeService;
import ru.kashtanov.just_fire_service.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Viktor Кashtanov
 */
@Service
public class GratitudeServiceImpl implements GratitudeService {
    private final GratitudeRepo gratitudeRepo;
    private final UserService userService;
    private final GratitudeRecipientService gratitudeRecipientService;


    public GratitudeServiceImpl(GratitudeRepo gratitudeRepo, UserService userService, GratitudeRecipientService gratitudeRecipientService) {
        this.gratitudeRepo = gratitudeRepo;
        this.userService = userService;
        this.gratitudeRecipientService = gratitudeRecipientService;

    }

    @Override
    public GratitudeDto createGratitude(GratitudeSaveDto dto) {
        Gratitude gratitude = saveGratitude(dto);
        return convertToDto(gratitude);
    }


    @Override
    public Gratitude saveGratitude(GratitudeSaveDto dto) {
        User user = userService.getOrCreate(dto.getAuthorId());
        var gratitude = new Gratitude();
        gratitude.setAuthor(user);
        gratitude.setContent(dto.getContent());
        gratitude.setTimestamp(System.currentTimeMillis());

        Gratitude saved = gratitudeRepo.save(gratitude);

        List<User> list = dto.getRecipientsIds().stream()
                .map(userService::getOrCreate).toList();


        List<GratitudeRecipient> recipientList = new ArrayList<>();
        list.forEach(recipient -> {
            var link = new GratitudeRecipient();
            link.setRecipient(recipient);
            link.setGratitude(saved);
            GratitudeRecipient gratitudeRecipient = gratitudeRecipientService.createGratitudeRecipient(link);
            recipientList.add(gratitudeRecipient);
        });
        saved.setRecipientLinks(recipientList);
        return saved;
    }

    @Override
    public List<GratitudeDto> findAllGratitudes(long userId, int limit, int offset, String filter) {
        List<Gratitude> gratitudes = new ArrayList<>();
        GratitudeFilterType filterType = parseFilter(filter);
        User user = userService.getOrCreate(userId);
        userId = user.getId();
        switch (filterType) {
            case ALL -> gratitudes = gratitudeRepo.findPageableGratitudes(limit, offset);
            case SENT ->gratitudes = gratitudeRepo.findUserSentGratitudesNative(userId, limit, offset);
            case RECEIVED -> gratitudes = gratitudeRepo.findUserReceivedGratitudesNative(userId, limit, offset);
        }

        return gratitudes.stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public List<GratitudeDto> findReceivedGratitudesByUser(Long userId, int limit, int offset) {
        List<Gratitude> gratitudes = gratitudeRepo.findUserReceivedGratitudesNative(userId, limit, offset);
        return gratitudes.stream().map(this::convertToDto).toList();

    }

    @Override
    public List<GratitudeDto> findSentGratitudesByUser(Long userId, int limit, int offset) {
        List<Gratitude> gratitudes = gratitudeRepo.findUserSentGratitudesNative(userId, limit, offset);
        return gratitudes.stream().map(this::convertToDto).toList();
    }

    public Optional<Gratitude> findGratitudeInRepoById(Long id) {
        return gratitudeRepo.findById(id);
    }

    @Override
    public Optional<GratitudeDto> findGratitudeById(Long id) {
        return gratitudeRepo.findById(id).map(this::convertToDto);
    }


    @Override
    public GratitudeDto convertToDto(Gratitude gratitude) {
        var dto = new GratitudeDto();
        dto.setId(gratitude.getId());
        dto.setContent(gratitude.getContent());
        dto.setTimestamp(gratitude.getTimestamp());

        UserDto authorDto = userService.buildUserDto(gratitude.getAuthor());
        dto.setAuthor(authorDto);
        dto.setLikesCount(gratitude.getLikes().size());
        List<UserDto> recipients = buildRecipientsDto(gratitude.getRecipientLinks());
        dto.setRecipients(recipients);
        return dto;
    }


    public List<UserDto> buildRecipientsDto(List<GratitudeRecipient> recipients) {
        if (recipients == null) {
            return new ArrayList<>();
        }
        return recipients.stream()
                .map(GratitudeRecipient::getRecipient)  // via hibernate we get it , since in the children table we store only user IDs
                .map(userService::buildUserDto)
                .toList();
    }

    private GratitudeFilterType parseFilter(String filter) {
        if (filter == null) return GratitudeFilterType.ALL;

        try {
            return GratitudeFilterType.valueOf(filter.toUpperCase());
        } catch (IllegalArgumentException e) {
            return GratitudeFilterType.ALL;
        }
    }


}
