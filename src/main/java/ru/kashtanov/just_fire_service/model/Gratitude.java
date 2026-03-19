package ru.kashtanov.just_fire_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
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
@Table(name = "gratitudes")
public class Gratitude {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gratitude_factory")
    @SequenceGenerator(name = "gratitude_factory", sequenceName = "gratitude_id_factory")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @OneToMany(mappedBy = "gratitude")
    private List<GratitudeRecipient> recipientLinks;

    @OneToMany(mappedBy = "gratitude")
    private List<Like> likes;

    @CreationTimestamp
    @Column(name = "timestamp")
    private Long timestamp;

    @Column(name = "content")
    private String content;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Gratitude gratitude)) return false;
        return Objects.equals(id, gratitude.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Gratitude{" +
                "id=" + id+
                '}';
    }
}
