package Project.TotalWar.Model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Lord")
public class LordModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lord_id")
    private Long lordId;

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

    // Constructors, getters, and setters
    public LordModel() {
    }

    public LordModel(Long lordId, String lordName, Boolean isFactionLeader, RaceModel race, FactionModel faction) {
        this.lordId = lordId;
        this.lordName = lordName;
        this.isFactionLeader = isFactionLeader;
        this.race = race;
        this.faction = faction;
    }

    public Long getLordId() {
        return lordId;
    }

    public void setLordId(Long lordId) {
        this.lordId = lordId;
    }

    public String getLordName() {
        return lordName;
    }

    public void setLordName(String lordName) {
        this.lordName = lordName;
    }

    public Boolean getFactionLeader() {
        return isFactionLeader;
    }

    public void setFactionLeader(Boolean factionLeader) {
        isFactionLeader = factionLeader;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        LordModel other = (LordModel) obj;
        return Objects.equals(lordId, other.lordId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lordId);
    }

    @Override
    public String toString() {
        return "LordModel{" +
                "lordId='" + lordId + '\'' +
                ", lordName='" + lordName + '\'' +
                ", isFactionLeader=" + isFactionLeader +
                ", race=" + race +
                ", faction=" + faction +
                '}';
    }
}