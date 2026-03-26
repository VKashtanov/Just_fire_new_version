package ru.kashtanov.just_fire_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kashtanov.just_fire_service.model.User;

import java.util.List;
import java.util.Optional;

/**
 * @author Viktor Кashtanov
 */
@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    @Query(value = "SELECT u.*, COUNT(gr.author_id) as thanker " +
            "FROM just_fire_users u " +
            "JOIN gratitudes gr ON u.id = gr.author_id " +
            "GROUP BY u.id " +
            "ORDER BY thanker DESC " +
            "LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<User> findTopThanksGivers(@Param("limit") int limit, @Param("offset") int offset);

    @Query(value = "SELECT u.*, COUNT(gr.recipient_id) as gratitude_recepient " +
            "FROM just_fire_users u " +
            "JOIN gratitude_recipients gr ON u.id = gr.recipient_id " +
            "GROUP BY u.id " +
            "ORDER BY gratitude_recepient DESC " +
            "LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<User> findTopGratitudeRecipients(@Param("limit") int limit, @Param("offset") int offset);


    @Query(value = "SELECT * FROM just_fire_users jfu " +
            "WHERE jfu.user_id_from_common_db =:userId ", nativeQuery = true)
    Optional<User> findUserByCommonDbId(@Param("userId") Long userId);


}
