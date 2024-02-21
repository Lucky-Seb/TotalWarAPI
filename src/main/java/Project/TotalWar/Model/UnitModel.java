package Project.TotalWar.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "Unit")
public class UnitModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unit_id")
    private Integer unitId;

    @Column(name = "unit_name")
    private String unitName;

    @Column(name = "unit_cost")
    private Integer unitCost;

    @Column(name = "unit_tier")
    private Integer unitTier;

    @ManyToOne
    @JoinColumn(name = "faction_id")
    private FactionModel faction;

    @OneToOne
    @JoinColumn(name = "race_id")
    private RaceModel race;

    // Constructors
    public UnitModel() {
    }

    public UnitModel(String unitName, Integer unitCost, Integer unitTier, FactionModel faction, RaceModel race) {
        this.unitName = unitName;
        this.unitCost = unitCost;
        this.unitTier = unitTier;
        this.faction = faction;
        this.race = race;
    }

    // Getters and setters
    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Integer getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Integer unitCost) {
        this.unitCost = unitCost;
    }

    public Integer getUnitTier() {
        return unitTier;
    }

    public void setUnitTier(Integer unitTier) {
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
}
