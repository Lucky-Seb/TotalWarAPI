package Project.TotalWar.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "Hero")
public class HeroModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hero_id")
    private Long heroId;
    @Column(name = "hero_name")
    private String heroName;
    @Column(name = "hero_type")
    private String heroType;
    @Column(name = "unique_hero")
    private boolean uniqueHero;

    // JSON property representing boolean as 1 or 0
    private int uniqueHeroAsInt;

    @ManyToOne(fetch = FetchType.EAGER) // Specifies Many-to-One relationship with eager fetching
    @JoinColumn(name = "faction_Id", referencedColumnName = "faction_id") // Specifies the foreign key column and its reference
    @JsonBackReference // Add this annotation
    private FactionModel faction; // Reference to the associated faction

    @OneToOne
    @JoinColumn(name = "race_Id")
    private RaceModel race;

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

    public boolean getUniqueHero() {
        return uniqueHero;
    }

    public void setUniqueHero(boolean uniqueHero) {
        this.uniqueHero = uniqueHero;
        this.uniqueHeroAsInt = uniqueHero ? 1 : 0;
    }

    public int getUniqueHeroAsInt() {
        return uniqueHeroAsInt;
    }

    public void setUniqueHeroAsInt(int uniqueHeroAsInt) {
        if (uniqueHeroAsInt == 1) {
            this.uniqueHero = true;
        } else {
            this.uniqueHero = false;
        }
        this.uniqueHeroAsInt = uniqueHeroAsInt;
    }

    public FactionModel getFaction() {
        return faction;
    }

    public void setFaction(FactionModel faction) {
        this.faction = faction;
    }

    public RaceModel getRace() {
        return race;
    }

    public void setRace(RaceModel race) {
        this.race = race;
    }

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
                ", uniqueHero=" + uniqueHeroAsInt +
                ", faction=" + faction +
                '}';
    }

}