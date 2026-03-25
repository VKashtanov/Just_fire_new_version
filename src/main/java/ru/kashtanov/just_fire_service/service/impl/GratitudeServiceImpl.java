package ru.kashtanov.just_fire_service.service.impl;

import org.springframework.stereotype.Service;
import ru.kashtanov.just_fire_service.dto.GratitudeDto;
import ru.kashtanov.just_fire_service.dto.GratitudeSaveDto;
import ru.kashtanov.just_fire_service.dto.UserDto;
import ru.kashtanov.just_fire_service.exception.UserNotFoundException;
import ru.kashtanov.just_fire_service.model.Gratitude;
import ru.kashtanov.just_fire_service.model.User;
import ru.kashtanov.just_fire_service.model.join.GratitudeRecipient;
import ru.kashtanov.just_fire_service.repository.GratitudeRepo;
import ru.kashtanov.just_fire_service.service.GratitudeService;
import ru.kashtanov.just_fire_service.service.UserService;

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
    public Gratitude saveGratitude(GratitudeSaveDto dto) {
        try {
            User user = userService.getOrCreate(dto.getAuthorId());
            var gratitude = new Gratitude();
            gratitude.setAuthor(user);
            gratitude.setContent(dto.getContent());
            Gratitude saved = gratitudeRepo.save(gratitude);

            dto.getRecipientsIds().stream()
                    .map(userService::getOrCreate)
                    .forEach(recipient -> {
                        var link = new GratitudeRecipient();
                        link.setRecipient(recipient);
                        link.setGratitude(saved);
                        gratitudeRecipientService.createGratitudeRecipient(link);
                    });
            return saved;
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("User Not Found id: " + dto.getAuthorId());
        }
    }

    @Override
    public List<GratitudeDto> findAllGratitudes() {
        List<Gratitude> all = gratitudeRepo.findAll();
        return all.stream().map(this::convertToDto).toList();

    }

    @Override
    public List<GratitudeDto> findReceivedGratitudesByUser(Long userId) {
        List<Gratitude> gratitudes = gratitudeRepo.findUserReceivedGratitudesNative(userId);
        return gratitudes.stream().map(this::convertToDto).toList();

    }

    @Override
    public List<GratitudeDto> findSentGratitudesByUser(Long userId) {
        List<Gratitude> gratitudes = gratitudeRepo.findUserSentGratitudesNative(userId);
        return gratitudes.stream().map(this::convertToDto).toList();
    }

    @Override
    public Optional<GratitudeDto> findGratitudeById(Integer id) {
        Optional<Gratitude> byId = gratitudeRepo.findById(id);
        if (byId.isPresent()) {
            return Optional.of(convertToDto(byId.get()));
        }
        return Optional.empty();
    }


    private GratitudeDto convertToDto(Gratitude gratitude) {
        var dto = new GratitudeDto();
        dto.setId(gratitude.getId());
        dto.setContent(gratitude.getContent());
        dto.setTimestamp(gratitude.getTimestamp());

        UserDto authorDto = userService.buildUserDto(gratitude.getAuthor());
        dto.setAuthor(authorDto);

        List<UserDto> recipients = buildRecipientsDto(gratitude.getRecipientLinks());
        dto.setRecipients(recipients);
        return dto;
    }


    public List<UserDto> buildRecipientsDto(List<GratitudeRecipient> recipients) {
        return recipients.stream()
                .map(GratitudeRecipient::getRecipient)  // via hibernate we get it , since in the children table we store only user IDs
                .map(userService::buildUserDto)
                .toList();
    }
}
