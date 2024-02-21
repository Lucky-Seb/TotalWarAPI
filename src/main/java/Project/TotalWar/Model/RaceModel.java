package Project.TotalWar.Model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Race")
public class RaceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "race_id")
    private Long raceId;

    @Column(name = "race_name")
    private String raceName;

    @OneToOne
    @JoinColumn(name = "faction_id")
    private FactionModel faction;

    @OneToOne
    @JoinColumn(name = "hero_id")
    private HeroModel hero;

    // Constructors, getters, and setters (omitted for brevity)

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        RaceModel other = (RaceModel) obj;
        return Objects.equals(raceId, other.raceId) &&
                Objects.equals(raceName, other.raceName) &&
                Objects.equals(faction, other.faction) &&
                Objects.equals(hero, other.hero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(raceId, raceName, faction, hero);
    }

    @Override
    public String toString() {
        return "RaceModel{" +
                "raceId=" + raceId +
                ", raceName='" + raceName + '\'' +
                ", faction=" + faction +
                ", hero=" + hero +
                '}';
    }
}
