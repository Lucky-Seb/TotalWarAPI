package Project.TotalWar.Controller;

import Project.TotalWar.DTO.HeroDTO;
import Project.TotalWar.Model.FactionModel;
import Project.TotalWar.Model.HeroModel;
import Project.TotalWar.Repository.FactionRepository;
import Project.TotalWar.Repository.HeroRepository;
import Project.TotalWar.util.HeroModelAssembler;
import Project.TotalWar.util.NotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class HeroController {

    private final HeroRepository heroRepository;
    private final HeroModelAssembler assembler;
    private final FactionRepository factionRepository; // Add this line

    HeroController(HeroRepository heroRepository, HeroModelAssembler assembler, FactionRepository factionRepository) {
        this.heroRepository = heroRepository;
        this.assembler = assembler;
        this.factionRepository = factionRepository; // Initialize factionRepository
    }

    @GetMapping("/heros")
    public CollectionModel<EntityModel<HeroModel>> all() {
        List<EntityModel<HeroModel>> heroes = heroRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(heroes,
                linkTo(methodOn(HeroController.class).all()).withSelfRel());
    }

    @GetMapping("/hero/{id}")
    public EntityModel<HeroDTO> one(@PathVariable Long id) {
        HeroModel hero = heroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        // Convert HeroModel to HeroDTO
        HeroDTO heroDTO = convertToHeroDTO(hero);

        // Include faction information in the DTO
        FactionModel faction = hero.getFaction();
        if (faction != null) {
            heroDTO.setFactionId(faction.getFactionId());
            heroDTO.setFactionName(faction.getFactionName());
            // Include other faction information as needed
        }

        // Create EntityModel for HeroDTO
        EntityModel<HeroDTO> entityModel = EntityModel.of(heroDTO);

        // Add self link
        entityModel.add(linkTo(methodOn(HeroController.class).one(id)).withSelfRel());

        return entityModel;
    }


    @PostMapping("/hero")
    public ResponseEntity<EntityModel<HeroModel>> newHero(@RequestBody HeroDTO heroDTO) {
        // Map data from HeroDTO to HeroModel
        HeroModel newHero = convertToHeroModel(heroDTO);

        // Fetch the faction entity using the provided faction ID
        FactionModel faction = factionRepository.findById(heroDTO.getFactionId())
                .orElseThrow(() -> new NotFoundException(heroDTO.getFactionId()));

        // Set the faction for the hero
        newHero.setFaction(faction);

        // Save the hero entity
        HeroModel savedHero = heroRepository.save(newHero);

        // Return the saved hero entity wrapped in an EntityModel
        return ResponseEntity
                .created(linkTo(methodOn(HeroController.class).one(savedHero.getHeroId())).toUri())
                .body(assembler.toModel(savedHero));
    }

    @PutMapping("/hero/{id}")
    ResponseEntity<EntityModel<HeroModel>> updateHero(@PathVariable Long id, @RequestBody HeroDTO updatedHeroDTO) {
        // Map data from HeroDTO to HeroModel
        HeroModel existingHero = heroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
        updateHeroFromDTO(existingHero, updatedHeroDTO);

        // Save the updated hero entity
        HeroModel savedHero = heroRepository.save(existingHero);

        return ResponseEntity.ok(assembler.toModel(savedHero));
    }


    @DeleteMapping("/hero/{id}")
    ResponseEntity<?> deleteHero(@PathVariable Long id) {
        heroRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Utility method to convert HeroDTO to HeroModel
    private HeroModel convertToHeroModel(HeroDTO heroDTO) {
        HeroModel hero = new HeroModel();
        hero.setHeroName(heroDTO.getHeroName());
        hero.setHeroType(heroDTO.getHeroType());
        hero.setUniqueHero(heroDTO.isUniqueHero());
        // Set other properties as needed
        return hero;
    }

    // Utility method to update HeroModel from HeroDTO
    private void updateHeroFromDTO(HeroModel hero, HeroDTO heroDTO) {
        hero.setHeroName(heroDTO.getHeroName());
        hero.setHeroType(heroDTO.getHeroType());
        hero.setUniqueHero(heroDTO.isUniqueHero());
        // Update other properties as needed
    }

    private HeroDTO convertToHeroDTO(HeroModel hero) {
        HeroDTO heroDTO = new HeroDTO();
        heroDTO.setHeroId(hero.getHeroId());
        heroDTO.setHeroName(hero.getHeroName());
        heroDTO.setHeroType(hero.getHeroType());
        heroDTO.setUniqueHero(hero.getUniqueHero());
        // Set faction ID if needed
        FactionModel faction = hero.getFaction();
        if (faction != null) {
            heroDTO.setFactionId(faction.getFactionId());
            heroDTO.setFactionName(faction.getFactionName());
        }
        return heroDTO;
    }

}