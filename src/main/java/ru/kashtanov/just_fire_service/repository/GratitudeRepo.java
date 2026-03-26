package ru.kashtanov.just_fire_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kashtanov.just_fire_service.model.Gratitude;

import java.util.List;

/**
 * @author Viktor Кashtanov
 */
@Repository
public interface GratitudeRepo extends JpaRepository<Gratitude, Long> {


    @Query(value = "SELECT * FROM gratitudes ORDER BY timestamp DESC LIMIT :limit OFFSET :offset",
            nativeQuery = true)
    List<Gratitude> findPageableGratitudes(@Param("limit") int limit, @Param("offset") int offset);


    @Query(value =
            "SELECT * FROM gratitudes g " +
                    "JOIN gratitude_recipients gr " +
                    "ON g.id = gr.gratitude_id " +
                    "WHERE gr.recipient_id=:userId " +
                    "ORDER BY timestamp DESC LIMIT :limit OFFSET :offset", nativeQuery = true)
    public abstract List<Gratitude> findUserReceivedGratitudesNative(@Param("userId") Long userId);


    @Query(value = "SELECT * FROM gratitudes WHERE author_id=:userId " +
            "ORDER BY timestamp DESC LIMIT :limit OFFSET :offset", nativeQuery = true)
    public abstract List<Gratitude> findUserSentGratitudesNative(@Param("userId") Long userId);

}
