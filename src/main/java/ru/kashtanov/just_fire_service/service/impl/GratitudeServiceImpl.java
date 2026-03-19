package ru.kashtanov.just_fire_service.service.impl;

import org.springframework.stereotype.Service;
import ru.kashtanov.just_fire_service.dto.GratitudeDto;
import ru.kashtanov.just_fire_service.dto.UserDto;
import ru.kashtanov.just_fire_service.model.Gratitude;
import ru.kashtanov.just_fire_service.model.join.GratitudeRecipient;
import ru.kashtanov.just_fire_service.repository.GratitudeRepo;

import java.util.List;
import java.util.Optional;

/**
 * @author Viktor Кashtanov
 */
@Service
public class GratitudeServiceImpl {
    private final GratitudeRepo gratitudeRepo;


    public GratitudeServiceImpl(GratitudeRepo gratitudeRepo) {
        this.gratitudeRepo = gratitudeRepo;
    }

    public Gratitude saveGratitude(Gratitude gratitude) {
        return gratitudeRepo.save(gratitude);
    }

    public List<GratitudeDto> findAllGratitudes() {
        List<Gratitude> all = gratitudeRepo.findAll();
        return all.stream().map(this::convertToDto).toList();

    }

    public List<GratitudeDto> findReceivedGratitudesByUser(Long userId) {
        List<Gratitude> gratitudes = gratitudeRepo.findUserReceivedGratitudesNative(userId);
        return gratitudes.stream().map(this::convertToDto).toList();

    }

    public List<GratitudeDto> findSentGratitudesByUser(Long userId) {
        List<Gratitude> gratitudes = gratitudeRepo.findUserSentGratitudesNative(userId);
        return gratitudes.stream().map(this::convertToDto).toList();
    }


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

        UserDto authorDto = buildUserDto(gratitude);
        dto.setAuthor(authorDto);

        List<UserDto> recipients = buildRecipientsDto(gratitude);
        dto.setRecipients(recipients);
        return dto;


    }

    private UserDto buildUserDto(Gratitude gratitude) {
        UserDto authorDto = new UserDto();
        authorDto.setUserId(gratitude.getAuthor().getId());
        authorDto.setFirstName(gratitude.getAuthor().getFirstName());
        authorDto.setLastName(gratitude.getAuthor().getLastName());
        authorDto.setFullName(gratitude.getAuthor().getFirstName() + " " + gratitude.getAuthor().getLastName());
        authorDto.setEmail(gratitude.getAuthor().getEmail());
        authorDto.setPosition(gratitude.getAuthor().getPosition());
        authorDto.setPortraitUrl(gratitude.getAuthor().getPortraitUrl());
        authorDto.setPhone(gratitude.getAuthor().getPhone() != null ? gratitude.getAuthor().getPhone() : "");
        return authorDto;
    }

    public List<UserDto> buildRecipientsDto(Gratitude gratitude) {
        return gratitude.getRecipientLinks().stream()
                .map(GratitudeRecipient::getRecipient)  // via hibernate we get it , since in the children table we store only user IDs
                .map(user -> {
                    UserDto userDto = new UserDto();
                    userDto.setUserId(user.getId());
                    userDto.setFirstName(user.getFirstName());
                    userDto.setMiddleName(user.getMiddleName());
                    userDto.setLastName(user.getLastName());
                    userDto.setFullName(user.getFirstName() + " " +
                            (user.getMiddleName() != null ? user.getMiddleName() + " " : "") +
                            user.getLastName());
                    userDto.setEmail(user.getEmail());
                    userDto.setPosition(user.getPosition());
                    userDto.setPortraitUrl(user.getPortraitUrl());
                    userDto.setPhone(user.getPhone() != null ? user.getPhone() : "");
                    return userDto;
                })
                .toList();
    }
}
