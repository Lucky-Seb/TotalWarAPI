package Project.TotalWar.Model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Unit")
public class UnitModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unit_id")
    private Long unitId;

    @Column(name = "unit_name")
    private String unitName;

    @Column(name = "unit_cost")
    private Long unitCost;

    @Column(name = "unit_tier")
    private Long unitTier;

    @ManyToOne
    @JoinColumn(name = "faction_id")
    private FactionModel faction;

    @OneToOne
    @JoinColumn(name = "race_id")
    private RaceModel race;


    // Getters and setters
    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Long getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Long unitCost) {
        this.unitCost = unitCost;
    }

    public Long getUnitTier() {
        return unitTier;
    }

    public void setUnitTier(Long unitTier) {
        this.unitTier = unitTier;
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
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        UnitModel other = (UnitModel) obj;
        return Objects.equals(unitId, other.unitId) &&
                Objects.equals(unitName, other.unitName) &&
                Objects.equals(unitCost, other.unitCost) &&
                Objects.equals(unitTier, other.unitTier) &&
                Objects.equals(faction, other.faction) &&
                Objects.equals(race, other.race);
    }

    @Override
    public int hashCode() {
        return Objects.hash(unitId, unitName, unitCost, unitTier, faction, race);
    }


    @Override
    public String toString() {
        return "UnitModel{" +
                "unitId=" + unitId +
                ", unitName='" + unitName + '\'' +
                ", unitCost=" + unitCost +
                ", unitTier=" + unitTier +
                ", faction=" + faction +
                ", race=" + race +
                '}';
    }
}
