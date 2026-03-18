package ru.kashtanov.just_fire_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * @author Viktor Кashtanov
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
public class SiteConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "config_factory")
    @SequenceGenerator(name = "config_factory", sequenceName = "config_id_factory")
    private Long id;

    @Column(name = "site_id", unique = true)
    private Long siteId;
    @Column(name = "generalDirector_id")
    private Long generalDirector;
    @Column(name = "siteResponsible_id")
    private Long siteResponsible;

    public SiteConfig(Long siteId,Long generalDirector, Long siteResponsible) {
        this.siteId = siteId;
        this.generalDirector = generalDirector;
        this.siteResponsible = siteResponsible;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SiteConfig config)) return false;
        return Objects.equals(id, config.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "SiteConfig{" +
                "id=" + id+
                '}';
    }
}
