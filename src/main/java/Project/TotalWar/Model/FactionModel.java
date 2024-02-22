package Project.TotalWar.Model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Faction")
public class FactionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "faction_id")
    private Long factionId;

    @Column(name = "faction_name")
    private String factionName;

    @OneToMany(mappedBy = "faction", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference // Add this annotation
    private List<HeroModel> heroes;

    // Constructors, getters, and setters
    public FactionModel() {
    }

    public FactionModel(String factionName, List<HeroModel> heroes) {
        this.factionName = factionName;
        this.heroes = heroes;
    }

    public Long getFactionId() {
        return factionId;
    }

    public void setFactionId(Long factionId) {
        this.factionId = factionId;
    }

    public String getFactionName() {
        return factionName;
    }

    public void setFactionName(String factionName) {
        this.factionName = factionName;
    }

    public List<HeroModel> getHeroes() {
        return heroes;
    }

    public void setHeroes(List<HeroModel> heroes) {
        this.heroes = heroes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.factionId, this.factionName, this.heroes);
    }

    @Override
    public String toString() {
        return "FactionModel{" +
                "factionId=" + factionId +
                ", factionName='" + factionName + '\'' +
                ", heroes=" + heroes +
                '}';
    }
}