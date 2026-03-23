package ru.kashtanov.just_fire_service.repository;

import org.springframework.data.domain.Pageable;
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

    List<User> findByFullNameContains(String fullName, Pageable pageable);

    @Query(value = "SELECT * FROM just_fire_users jfu " +
            "WHERE jfu.user_id_from_common_db =: userId ", nativeQuery = true)
    Optional<User> findUserByCommonDbId(@Param("userId") Long userId);


}
