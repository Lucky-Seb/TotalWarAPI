package Project.TotalWar.DTO;

public class HeroDTO {
    private Long heroId;
    private String heroName;
    private String heroType;
    private boolean uniqueHero;

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
}
