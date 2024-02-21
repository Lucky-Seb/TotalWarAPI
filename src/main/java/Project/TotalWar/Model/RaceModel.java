package Project.TotalWar.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "Race")
public class RaceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "race_id")
    private Long raceId;

    @Column(name = "race_name")
    private String raceName;

    // Assuming that a race can have only one associated faction, use @OneToOne
    @OneToOne
    @JoinColumn(name = "faction_id")
    private FactionModel faction;

    // Assuming that a hero can have only one associated faction, use @OneToOne
    @OneToOne
    @JoinColumn(name = "hero_id")
    private HeroModel hero;

    // Constructors

    public RaceModel() {
    }

    public RaceModel(Long raceId, String raceName, FactionModel faction, HeroModel hero) {
        this.raceId = raceId;
        this.raceName = raceName;
        this.faction = faction;
        this.hero = hero;
    }

// Getters and setters

    public Long getRaceId() {
        return raceId;
    }

    public void setRaceId(Long raceId) {
        this.raceId = raceId;
    }

    public String getRaceName() {
        return raceName;
    }

    public void setRaceName(String raceName) {
        this.raceName = raceName;
    }

    public FactionModel getFaction() {
        return faction;
    }

    public void setFaction(FactionModel faction) {
        this.faction = faction;
    }

    public HeroModel getHero() {
        return hero;
    }

    public void setHero(HeroModel hero) {
        this.hero = hero;
    }
}
