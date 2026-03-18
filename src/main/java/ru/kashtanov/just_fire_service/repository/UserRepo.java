package ru.kashtanov.just_fire_service.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kashtanov.just_fire_service.model.User;

import java.util.List;

/**
 * @author Viktor Кashtanov
 */
@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    List<User> findByFullNameContains(String fullName, Pageable pageable);

    public User findByEmail(String email);

}
