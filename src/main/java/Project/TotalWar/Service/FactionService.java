package Project.TotalWar.Service;

import Project.TotalWar.Model.FactionModel;
import Project.TotalWar.DTO.*;
import Project.TotalWar.DTO.FactionWithHerosDTO;
import Project.TotalWar.Model.HeroModel;
import Project.TotalWar.Repository.FactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FactionService {

    @Autowired
    private FactionRepository factionRepository;

    @Transactional(readOnly = true)
    public List<FactionWithHerosDTO> getAllFactionsWithHeroes() {
        List<FactionModel> factions = factionRepository.findAll();
        return factions.stream()
                .map(this::convertToFactionWithHerosDTO)
                .collect(Collectors.toList());
    }

    private FactionWithHerosDTO convertToFactionWithHerosDTO(FactionModel faction) {
        FactionWithHerosDTO factionDTO = new FactionWithHerosDTO();
        factionDTO.setFactionId(faction.getFactionId());
        factionDTO.setFactionName(faction.getFactionName());
        factionDTO.setHeroes(faction.getHeroes().stream()
                .map(this::convertToHeroDTO)
                .collect(Collectors.toList()));
        return factionDTO;
    }

    private HeroDTO convertToHeroDTO(HeroModel hero) {
        HeroDTO heroDTO = new HeroDTO();
        heroDTO.setHeroId(hero.getHeroId());
        heroDTO.setHeroName(hero.getHeroName());
        heroDTO.setHeroType(hero.getHeroType());
        heroDTO.setUniqueHero(hero.getUniqueHero());
        return heroDTO;
    }
}
