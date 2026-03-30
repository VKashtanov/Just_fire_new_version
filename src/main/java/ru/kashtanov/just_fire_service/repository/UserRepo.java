package ru.kashtanov.just_fire_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kashtanov.just_fire_service.dto.liferay_dto.TopThankerUserDto;
import ru.kashtanov.just_fire_service.model.User;

import java.util.List;
import java.util.Optional;

/**
 * @author Viktor Кashtanov
 */
@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    @Query(value = "SELECT " +
            "CAST(u.user_id_from_common_db AS VARCHAR) as userId, " +
            "u.first_name as firstName, " +
            "u.last_name as lastName, " +
            "u.full_name as fullName, " +
            "u.email as email, " +
            "u.position as position, " +
            "u.portrait_url as portraitUrl, " +
            "CAST(COUNT(gr.author_id) AS BIGINT) as thankedQty, " +
            "CAST(0 AS BIGINT) as wasThankedQty " +
            "FROM just_fire_users u " +
            "JOIN gratitudes gr ON u.id = gr.author_id " +
            "GROUP BY u.id, u.user_id_from_common_db, u.first_name, u.last_name, " +
            "u.full_name, u.email, u.position, u.portrait_url " +
            "ORDER BY thankedQty DESC " +
            "LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<TopThankerUserDto> findTopThanksGivers(@Param("limit") int limit, @Param("offset") int offset);



    @Query(value = "SELECT " +
            "CAST(u.user_id_from_common_db AS VARCHAR) as userId, " +
            "u.first_name as firstName, " +
            "u.last_name as lastName, " +
            "u.full_name as fullName, " +
            "u.email as email, " +
            "u.position as position, " +
            "u.portrait_url as portraitUrl, " +
            "CAST(0 AS BIGINT) as thankedQty, " +
            "CAST(COUNT(gr.recipient_id) AS BIGINT) as wasThankedQty " +
            "FROM just_fire_users u " +
            "JOIN gratitude_recipients gr ON u.id = gr.recipient_id " +
            "GROUP BY u.id, u.user_id_from_common_db, u.first_name, u.last_name, " +
            "u.full_name, u.email, u.position, u.portrait_url " +
            "ORDER BY wasThankedQty DESC " +
            "LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<TopThankerUserDto> findTopGratitudeRecipients(@Param("limit") int limit, @Param("offset") int offset);

    @Query(value = "SELECT * FROM just_fire_users jfu " +
            "WHERE jfu.user_id_from_common_db =:userId ", nativeQuery = true)
    Optional<User> findUserByCommonDbId(@Param("userId") Long userId);


}
