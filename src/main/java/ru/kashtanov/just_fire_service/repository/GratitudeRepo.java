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
public interface GratitudeRepo extends JpaRepository<Gratitude, Integer> {
//    @Query("SELECT g FROM Gratitude g WHERE g.author.id=:userId")
//    public List<Gratitude> findUserSentGratitudes(@Param("userId") Long userId);

    @Query(value =
            "SELECT * FROM gratitudes g "  +
            "JOIN gratitude_recipients gr "+
            "ON g.id = gr.gratitude_id "   +
            "WHERE gr.recipient_id=:userId",nativeQuery = true)
    public abstract List<Gratitude> findUserReceivedGratitudesNative(@Param("userId") Long userId);


    @Query(value = "SELECT * FROM gratitudes WHERE author_id=:userId",nativeQuery = true)
    public abstract List<Gratitude> findUserSentGratitudesNative(@Param("userId") Long userId);

}
