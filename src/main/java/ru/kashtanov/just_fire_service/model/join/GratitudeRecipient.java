package ru.kashtanov.just_fire_service.model.join;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.kashtanov.just_fire_service.model.Gratitude;
import ru.kashtanov.just_fire_service.model.User;

import java.util.Objects;

/**
 * @author Viktor Кashtanov
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"gratitude_id", "recipient_id"})},
        name = "gratitude_recipients")
public class GratitudeRecipient {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gratitudeRecipient_factory")
    @SequenceGenerator(
            name = "gratitudeRecipient_factory",
            sequenceName = "gratitudeRecipient_factory_id_factory",
            allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gratitude_id")
    private Gratitude gratitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id")
    private User recipient;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GratitudeRecipient gratitude)) return false;
        return Objects.equals(id, gratitude.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "GratitudeRecipient{" +
                "id=" + id +
                '}';
    }
}

