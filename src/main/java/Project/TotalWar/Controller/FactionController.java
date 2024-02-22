package Project.TotalWar.Controller;

import Project.TotalWar.DTO.FactionDTO;
import Project.TotalWar.DTO.HeroDTO;
import Project.TotalWar.Model.FactionModel;
import Project.TotalWar.Model.HeroModel;
import Project.TotalWar.Repository.FactionRepository;
import Project.TotalWar.Repository.HeroRepository;
import Project.TotalWar.util.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class FactionController {

    private final FactionRepository factionRepository;
    private final HeroRepository heroRepository; // Add this line

    FactionController(FactionRepository factionRepository, HeroRepository heroRepository) { // Update constructor
        this.factionRepository = factionRepository;
        this.heroRepository = heroRepository; // Initialize heroRepository
    }

    @GetMapping("/factions")
    public ResponseEntity<List<FactionDTO>> all() {
        List<FactionModel> factions = factionRepository.findAll();
        List<FactionDTO> factionDTOs = factions.stream()
                .map(this::convertToFactionDTO)
                .collect(Collectors.toList());

        // Populate heroes for each faction
        for (FactionDTO factionDTO : factionDTOs) {
            Long factionId = factionDTO.getFactionId();
            List<HeroModel> heroes = heroRepository.findByFactionId(factionId);
            List<HeroDTO> heroDTOs = heroes.stream()
                    .map(this::convertToHeroDTO)
                    .collect(Collectors.toList());
            factionDTO.setHeroes(heroDTOs);
        }

        return ResponseEntity.ok(factionDTOs);
    }

    @GetMapping("/faction/{id}")
    public ResponseEntity<FactionDTO> one(@PathVariable Long id) {
        FactionModel faction = factionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
        FactionDTO factionDTO = convertToFactionDTO(faction);

        return ResponseEntity.ok(factionDTO);
    }

    @PostMapping("/faction")
    public ResponseEntity<FactionDTO> newFaction(@RequestBody FactionDTO factionDTO) {
        FactionModel faction = convertToFactionModel(factionDTO);

        // Ensure the heroes are not null before attempting to associate them with the faction
        if (factionDTO.getHeroes() != null) {
            List<HeroModel> heroes = factionDTO.getHeroes().stream()
                    .map(this::convertToHeroModel)
                    .collect(Collectors.toList());
            faction.setHeroes(heroes);
        }

        FactionModel savedFaction = factionRepository.save(faction);
        FactionDTO savedFactionDTO = convertToFactionDTO(savedFaction);

        return ResponseEntity
                .created(linkTo(methodOn(FactionController.class).one(savedFactionDTO.getFactionId())).toUri())
                .body(savedFactionDTO);
    }

    @PutMapping("/faction/{id}")
    public ResponseEntity<FactionDTO> updateFaction(@PathVariable Long id, @RequestBody FactionDTO updatedFactionDTO) {
        FactionModel existingFaction = factionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        // Update existingFaction with properties from updatedFactionDTO
        existingFaction.setFactionName(updatedFactionDTO.getFactionName());
        // Update other properties as needed

        FactionModel savedFaction = factionRepository.save(existingFaction);
        FactionDTO savedFactionDTO = convertToFactionDTO(savedFaction);

        return ResponseEntity.ok(savedFactionDTO);
    }

    @DeleteMapping("/faction/{id}")
    public ResponseEntity<?> deleteFaction(@PathVariable Long id) {
        factionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Utility methods for conversion between DTO and Entity
    private FactionDTO convertToFactionDTO(FactionModel faction) {
        FactionDTO factionDTO = new FactionDTO();
        factionDTO.setFactionId(faction.getFactionId());
        factionDTO.setFactionName(faction.getFactionName());
        // You might need to map heroes here if you don't want to load them eagerly
        // factionDTO.setHeroes(convertToHeroDTOList(faction.getHeroes()));
        return factionDTO;
    }

    private HeroDTO convertToHeroDTO(HeroModel hero) {
        HeroDTO heroDTO = new HeroDTO();
        heroDTO.setHeroId(hero.getHeroId());
        heroDTO.setHeroName(hero.getHeroName());
        // Set other properties as needed
        return heroDTO;
    }


    private FactionModel convertToFactionModel(FactionDTO factionDTO) {
        FactionModel faction = new FactionModel();
        faction.setFactionId(factionDTO.getFactionId());
        faction.setFactionName(factionDTO.getFactionName());
        // You might need to map heroes here if you don't want to load them eagerly
        // faction.setHeroes(convertToHeroModelList(factionDTO.getHeroes()));
        return faction;
    }

    private HeroModel convertToHeroModel(HeroDTO heroDTO) {
        HeroModel hero = new HeroModel();
        hero.setHeroId(heroDTO.getHeroId());
        hero.setHeroName(heroDTO.getHeroName());
        hero.setHeroType(heroDTO.getHeroType());
        hero.setUniqueHero(heroDTO.isUniqueHero());
        // Set other properties as needed

        return hero;
    }

}