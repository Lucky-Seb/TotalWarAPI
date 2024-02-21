package Project.TotalWar.Model;


import java.util.Objects;

import jakarta.persistence.*;

@Entity
@Table(name = "Hero")
public class HeroModel {
    @Id
    @GeneratedValue
    private Long id;

    private String heroName;
    private String heroType;
    private boolean uniqueHero;

    @ManyToOne
    @JoinColumn(name = "factionId")
    private FactionModel faction;

    private int raceId;

    // getters and setters
    public FactionModel getFaction() {
        return faction;
    }

    public void setFaction(FactionModel faction) {
        this.faction = faction;
    }
}