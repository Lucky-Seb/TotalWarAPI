package Project.TotalWar.DTO;

import java.util.List;

public class FactionWithHerosDTO {
    private Long factionId;
    private String factionName;
    private List<HeroDTO> heroes;

    // Constructors, getters, and setters
    public FactionWithHerosDTO() {
    }

    public FactionWithHerosDTO(Long factionId, String factionName, List<HeroDTO> heroes) {
        this.factionId = factionId;
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

    public List<HeroDTO> getHeroes() {
        return heroes;
    }

    public void setHeroes(List<HeroDTO> heroes) {
        this.heroes = heroes;
    }
}