package Project.TotalWar.Model;


import java.util.Objects;

import jakarta.persistence.*;

@Entity
@Table(name = "Hero")
public class HeroModel {
    @Id
    @GeneratedValue
    private Long heroId;

    private String heroName;
    private String heroType;
    private boolean uniqueHero;

    @ManyToOne
    @JoinColumn(name = "factionId")
    private FactionModel faction;

/*    @OneToOne
    @JoinColumn(name = "raceId")
    private RaceModel race;*/

    // getters and setters
    public Long getHeroId() {
        return heroId;
    }

    public void setHeroId(Long id) {
        this.heroId = id;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public String getHeroType() {
        return heroType;
    }

    public void setHeroType(String heroType) {
        this.heroType = heroType;
    }

    public boolean isUniqueHero() {
        return uniqueHero;
    }

/*    public void setUniqueHero(boolean uniqueHero) {
        // Add a boolean check here if needed
        if (uniqueHero) {
            // Do something when isUniqueHero is set to true
        } else {
            // Do something when isUniqueHero is set to false
        }
        this.uniqueHero = uniqueHero;
    }*/

    public FactionModel getFaction() {
        return faction;
    }

    public void setFaction(FactionModel faction) {
        this.faction = faction;
    }

/*    public RaceModel getRace() {
        return race;
    }

    public void setRace(RaceModel race) {
        this.race = race;
    }*/

    @Override
    public int hashCode() {
        return Objects.hash(this.heroId, this.heroName, this.heroType, this.uniqueHero, this.faction);
    }

    @Override
    public String toString() {
        return "HeroModel{" +
                "heroId=" + heroId +
                ", heroName='" + heroName + '\'' +
                ", heroType='" + heroType + '\'' +
                ", uniqueHero=" + uniqueHero +
                ", faction=" + faction +
                '}';
    }
}