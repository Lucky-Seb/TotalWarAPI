package Project.TotalWar.DTO;

public class HeroDTO {
    private Long heroId;
    private String heroName;
    private String heroType;
    private boolean uniqueHero;
    private Long factionId; // Add factionId property
    private String factionName; // Add factionName field

    // Getters and setters

    public Long getHeroId() {
        return heroId;
    }

    public void setHeroId(Long heroId) {
        this.heroId = heroId;
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

    public boolean isUniqueHero() {
        return uniqueHero;
    }

    public void setUniqueHero(boolean uniqueHero) {
        this.uniqueHero = uniqueHero;
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
}