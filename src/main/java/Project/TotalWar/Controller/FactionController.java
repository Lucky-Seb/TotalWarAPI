package Project.TotalWar.Controller;

import Project.TotalWar.DTO.FactionDTO;
import Project.TotalWar.DTO.FactionWithHerosDTO;
import Project.TotalWar.DTO.HeroDTO;
import Project.TotalWar.Model.FactionModel;
import Project.TotalWar.Model.HeroModel;
import Project.TotalWar.Repository.FactionRepository;
import Project.TotalWar.Repository.HeroRepository;
import Project.TotalWar.mappers.FactionMapper;
import Project.TotalWar.mappers.HeroMapper;
import Project.TotalWar.util.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class FactionController {

    private final FactionRepository factionRepository;
    private final HeroRepository heroRepository;
    @Autowired
    private FactionMapper factionMapper;
    @Autowired
    private HeroMapper heroMapper;

    @Autowired
    public FactionController(FactionRepository factionRepository, HeroRepository heroRepository) {
        this.factionRepository = factionRepository;
        this.heroRepository = heroRepository;
    }

    @GetMapping("/factions")
    public ResponseEntity<List<FactionDTO>> getAllFactions() {
        List<FactionModel> factions = factionRepository.findAllWithEagerRelationships();
        return ResponseEntity.ok(factionMapper.toList(factions, FactionDTO.class));
    }

    @GetMapping("/factions/{id}")
    public ResponseEntity<FactionDTO> getFactionById(@PathVariable Long id) {
        return factionRepository.findByIdWithEagerRelationships(id)
                .map(factionMapper::toDTO) // Use mapper for conversion
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NotFoundException(id));
    }
    @GetMapping("/factions/{id}/with-heroes")
    public ResponseEntity<FactionWithHerosDTO> getFactionWithHeroes(@PathVariable Long id) {
        FactionModel faction = factionRepository.findByIdWithEagerRelationships(id)
                .orElseThrow(() -> new NotFoundException(id));
        return ResponseEntity.ok(factionMapper.toFactionWithHeroesDTO(faction));
    }

    @PostMapping("/factions")
    public ResponseEntity<FactionDTO> createFaction(@RequestBody FactionDTO factionDTO) {
        FactionModel faction = factionRepository.save(factionMapper.toModel(factionDTO)); // Use mapper for conversion
        return ResponseEntity.created(linkTo(methodOn(FactionController.class).getFactionById(faction.getFactionId())).toUri())
                .body(factionMapper.toDTO(faction)); // Use mapper for conversion
    }

    @PutMapping("/factions/{id}")
    public ResponseEntity<FactionDTO> updateFaction(@PathVariable Long id, @RequestBody FactionDTO updatedFactionDTO) {
        FactionModel existingFaction = factionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        // Use mapper to update existingFaction with values from updatedFactionDTO
        factionMapper.updateModel(existingFaction, updatedFactionDTO);

        // Optionally, update any properties not handled by the mapper manually

        FactionModel savedFaction = factionRepository.save(existingFaction);
        return ResponseEntity.ok(factionMapper.toDTO(savedFaction));
    }

    @DeleteMapping("/factions/{id}")
    public ResponseEntity<?> deleteFaction(@PathVariable Long id) {
        factionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Add a hero to a faction
    @PostMapping("/factions/{factionId}/heroes")
    public ResponseEntity<HeroDTO> addHeroToFaction(@PathVariable Long factionId, @RequestBody HeroDTO heroDTO) {
        FactionModel faction = factionRepository.findById(factionId)
                .orElseThrow(() -> new NotFoundException(factionId));

        // Ensure heroDTO has the correct faction ID set
        heroDTO.setFactionId(factionId);  // Or similar logic to link the hero to the faction

        HeroModel hero = heroRepository.save(heroMapper.toModel(heroDTO)); // Use mapper for conversion
        return ResponseEntity.created(linkTo(methodOn(FactionController.class).getFactionHero(factionId, hero.getHeroId())).toUri())
                .body(heroMapper.toDTO(hero)); // Use mapper for conversion
    }

    // Get a specific hero within a faction
    @GetMapping("/factions/{factionId}/heroes/{heroId}")
    public ResponseEntity<HeroDTO> getFactionHero(@PathVariable Long factionId, @PathVariable Long heroId) {
        FactionModel faction = factionRepository.findById(factionId)
                .orElseThrow(() -> new NotFoundException(factionId));
        HeroModel hero = heroRepository.findById(heroId)
                .orElseThrow(() -> new NotFoundException(heroId));

        // Ensure the hero belongs to the faction
        if (!faction.getHeroes().contains(hero)) {
            throw new NotFoundException(heroId);
        }

        return ResponseEntity.ok(heroMapper.toDTO(hero));
    }

    // Update a hero within a faction
    @PutMapping("/factions/{factionId}/heroes/{heroId}")
    public ResponseEntity<HeroDTO> updateFactionHero(@PathVariable Long factionId, @PathVariable Long heroId, @RequestBody HeroDTO updatedHeroDTO) {
        // Fetch the existing hero
        HeroModel existingHero = heroRepository.findById(heroId)
                .orElseThrow(() -> new NotFoundException(heroId));

        // Update properties using mapper
        heroMapper.updateModel(existingHero, updatedHeroDTO);

        // Optionally, update any properties not handled by the mapper manually

        // Save the updated hero
        heroRepository.save(existingHero);

        return ResponseEntity.ok(heroMapper.toDTO(existingHero)); // Return the updated DTO
    }

    // Delete a hero from a faction
    @DeleteMapping("/factions/{factionId}/heroes/{heroId}")
    public ResponseEntity<?> deleteFactionHero(@PathVariable Long factionId, @PathVariable Long heroId) {
        // Ensure hero belongs to the faction (similar to getFactionHero)

        // Delete the hero using repository
        // ...

        return ResponseEntity.noContent().build();
    }
}