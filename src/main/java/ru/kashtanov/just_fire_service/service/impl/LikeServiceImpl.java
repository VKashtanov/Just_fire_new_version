package ru.kashtanov.just_fire_service.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.kashtanov.just_fire_service.dto.GratitudeDto;
import ru.kashtanov.just_fire_service.dto.LikeInfoDto;
import ru.kashtanov.just_fire_service.dto.LikeSaveDto;
import ru.kashtanov.just_fire_service.dto.UserDto;
import ru.kashtanov.just_fire_service.exception.GratitudeNotFoundException;
import ru.kashtanov.just_fire_service.model.Gratitude;
import ru.kashtanov.just_fire_service.model.Like;
import ru.kashtanov.just_fire_service.model.User;
import ru.kashtanov.just_fire_service.repository.LikeRepo;
import ru.kashtanov.just_fire_service.service.GratitudeService;

import java.util.List;
import java.util.Optional;

/**
 * @author Viktor Кashtanov
 */
@Service
@Transactional
public class LikeServiceImpl {
    private final LikeRepo likeRepo;
    private final UserServiceImpl userService;
    private final GratitudeService gratitudeService;

    public LikeServiceImpl(LikeRepo likeRepo, UserServiceImpl userService, GratitudeService gratitudeService) {
        this.likeRepo = likeRepo;
        this.userService = userService;
        this.gratitudeService = gratitudeService;
    }

    public LikeInfoDto toggleLike(LikeSaveDto dto) {
        var likeDto = new LikeInfoDto();
        Long gratitudeId = dto.getGratitudeId();
        Long userId = dto.getUserId();
        int qtyLastLikes = dto.getQtyLastLikes();

        boolean isUserLiked = false;
        Optional<User> user = userService.findUserByCommonDbId(userId);
        Optional<Like> userLike = Optional.empty();

        if (user.isPresent()) {
            userId = user.get().getId();
            userLike = likeRepo.findByUserIdAndGratitudeId(userId, gratitudeId);
        }
        // if user already liked then delete
        if (userLike.isPresent()) {
            likeRepo.deleteByUserIdAndGratitudeId(userId, gratitudeId);
            likeDto = convertToDto(userLike.get(), qtyLastLikes, isUserLiked);

        // else then add like
        } else {
            isUserLiked = true;
            Like like = addLike(dto);
            likeDto = convertToDto(like, qtyLastLikes, isUserLiked);
        }
        return likeDto;
    }


    public Like addLike(LikeSaveDto dto) {
        Long gratitudeId = dto.getGratitudeId();
        Long userId = dto.getUserId();

        User user = userService.getOrCreate(userId);
        Optional<Gratitude> gratitude = gratitudeService.findGratitudeInRepoById(gratitudeId);
        if (gratitude.isEmpty()) {
            throw new GratitudeNotFoundException("Gratitude not found with id: " + gratitudeId);
        }
        var like = new Like();
        like.setUser(user);
        like.setGratitude(gratitude.get());
        like.setTimestamp(System.currentTimeMillis());
        likeRepo.save(like);
        return like;
    }


    private LikeInfoDto convertToDto(Like like, int qtyLastLikedUsers, boolean isUserLiked) {
        var likeDto = new LikeInfoDto();
        GratitudeDto gratitudeDto = gratitudeService.convertToDto(like.getGratitude());

        Long likeCount = likeRepo.countByGratitudeId(like.getGratitude().getId());
        List<Like> recentLikes = likeRepo.findRecentLikesByGratitudeId(like.getGratitude().getId(), qtyLastLikedUsers);
        List<UserDto> list = recentLikes.stream().map(l -> {
            return userService.buildUserDto(l.getUser());
        }).toList();

        likeDto.setLikeCount(likeCount);
        likeDto.setLikedUsers(list);
        likeDto.setLikedByCurrentUser(isUserLiked);
        likeDto.setGratitude(gratitudeDto);

        return likeDto;
    }
}
