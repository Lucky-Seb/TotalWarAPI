package Project.TotalWar.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Faction")
public class FactionModel {
    @Id
    @GeneratedValue
    private Long factionId;

    private String factionName;

    @OneToMany(mappedBy = "faction", cascade = CascadeType.ALL)
    private List<HeroModel> heroes;

    // getters and setters
    public List<HeroModel> getHeroes() {
        return heroes;
    }

    public void setHeroes(List<HeroModel> heroes) {
        this.heroes = heroes;
    }
}