package Project.TotalWar.DTO;

import java.util.List;

public class FactionWithHerosDTO {
    private Long id;
    private String factionName;
    private List<HeroDTO> heroes;

    // Constructors, getters, and setters
    public FactionWithHerosDTO() {
    }

    public FactionWithHerosDTO(Long id, String factionName, List<HeroDTO> heroes) {
        this.id = id;
        this.factionName = factionName;
        this.heroes = heroes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFactionName() {
        return factionName;
    }

    public void setFactionName(String factionName) {
        this.factionName = factionName;
    }

    public List<HeroDTO> getHeroes() {
        return heroes;
    }

    public void setHeroes(List<HeroDTO> heroes) {
        this.heroes = heroes;
    }
}