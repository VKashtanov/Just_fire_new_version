package ru.kashtanov.just_fire_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kashtanov.just_fire_service.model.Like;
import ru.kashtanov.just_fire_service.model.User;

import java.util.List;
import java.util.Optional;

/**
 * @author Viktor Кashtanov
 */
@Repository
public interface LikeRepo extends JpaRepository<Like, Long> {
    //    @Query(value = "SELECT * FROM just_fire_users jfu " +
    //            "WHERE jfu.user_id_from_common_db =:userId ", nativeQuery = true)

    @Query(value = "SELECT COUNT(*) FROM just_fire_likes WHERE gratitude_id =:gratitudeId", nativeQuery = true)
    long countByGratitudeId(@Param("gratitudeId") Long gratitudeId);

    @Query(value = "SELECT * FROM just_fire_likes " +
            "WHERE user_id =:userId " +
            "AND gratitude_id =:gratitudeId", nativeQuery = true)

    Optional<Like> findByUserIdAndGratitudeId(@Param("userId") Long userId,
                                              @Param("gratitudeId") Long gratitudeId);


    @Modifying
    @Query(value = "DELETE FROM just_fire_likes " +
            "WHERE gratitude_id = :gratitudeId " +
            "AND user_id = :userId", nativeQuery = true)
    int deleteByUserIdAndGratitudeId(@Param("userId") Long userId,
                                     @Param("gratitudeId") Long gratitudeId);


    @Query(value = "SELECT * FROM just_fire_likes " +
            "WHERE gratitude_id = :gratitudeId " +
            "ORDER BY timestamp DESC " +
            "LIMIT :limit", nativeQuery = true)
    List<Like> findRecentLikesByGratitudeId(@Param("gratitudeId") Long gratitudeId,
                                            @Param("limit") int limit);
}
