package ru.kashtanov.just_fire_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.kashtanov.just_fire_service.model.join.GratitudeRecipient;

import java.util.List;
import java.util.Objects;

/**
 * @author Viktor Кashtanov
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "just_fire_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_factory")
    @SequenceGenerator(name = "user_factory", sequenceName = "user_id_factory")
    private Long id;

    @OneToMany(mappedBy = "recipient")
    private List<GratitudeRecipient> recipientLinks;

    @OneToMany(mappedBy = "author")
    private List<Gratitude> authoredGratitudes;

    @OneToMany(mappedBy="user")
    private List<Like> likes;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id+
                '}';
    }
}
