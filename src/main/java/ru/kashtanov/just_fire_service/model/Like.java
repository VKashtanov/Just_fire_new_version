package ru.kashtanov.just_fire_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

/**
 * @author Viktor Кashtanov
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"gratitude_id", "user_id"}
        )
}, name = "just_fire_likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "like_factory")
    @SequenceGenerator(name = "like_factory", sequenceName = "like_id_factory")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "gratitude_id")
    private Gratitude gratitude;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Like like)) return false;
        return Objects.equals(id, like.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Like{" +
                "id=" + id +
                '}';
    }
}
