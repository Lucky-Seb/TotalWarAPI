package Project.TotalWar.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "Lord")
public class LordModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lord_id")
    private String lordId;

    @Column(name = "lord_name")
    private String lordName;

    @Column(name = "is_faction_leader")
    private Boolean isFactionLeader;

    @OneToOne
    @JoinColumn(name = "race_id")
    private RaceModel race;

    @ManyToOne
    @JoinColumn(name = "faction_id")
    private FactionModel faction;

    // Constructors
    public LordModel() {
    }

    public LordModel(String lordId, String lordName, Boolean isFactionLeader, RaceModel race, FactionModel faction) {
        this.lordId = lordId;
        this.lordName = lordName;
        this.isFactionLeader = isFactionLeader;
        this.race = race;
        this.faction = faction;
    }

    // Getters and setters
    public String getLordId() {
        return lordId;
    }

    public void setLordId(String lordId) {
        this.lordId = lordId;
    }

    public String getLordName() {
        return lordName;
    }

    public void setLordName(String lordName) {
        this.lordName = lordName;
    }

    public Boolean getIsFactionLeader() {
        return isFactionLeader;
    }

    public void setIsFactionLeader(Boolean isFactionLeader) {
        this.isFactionLeader = isFactionLeader;
    }

    public RaceModel getRace() {
        return race;
    }

    public void setRace(RaceModel race) {
        this.race = race;
    }

    public FactionModel getFaction() {
        return faction;
    }

    public void setFaction(FactionModel faction) {
        this.faction = faction;
    }
}
