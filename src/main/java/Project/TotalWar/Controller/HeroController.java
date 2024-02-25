package Project.TotalWar.Controller;

import Project.TotalWar.DTO.HeroDTO;
import Project.TotalWar.Model.FactionModel;
import Project.TotalWar.Model.HeroModel;
import Project.TotalWar.Repository.FactionRepository;
import Project.TotalWar.Repository.HeroRepository;
import Project.TotalWar.util.HeroModelAssembler;
import Project.TotalWar.util.NotFoundException;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Controller class to handle HTTP requests related to heroes.
 */
@RestController
public class HeroController {

    private final HeroRepository heroRepository;
    private final HeroModelAssembler assembler;
    private final FactionRepository factionRepository;

    /**
     * Constructor for HeroController.
     *
     * @param heroRepository    The repository for managing hero entities.
     * @param assembler         The assembler for converting HeroModel to EntityModel.
     * @param factionRepository The repository for managing faction entities.
     */
    HeroController(HeroRepository heroRepository, HeroModelAssembler assembler, FactionRepository factionRepository) {
        this.heroRepository = heroRepository;
        this.assembler = assembler;
        this.factionRepository = factionRepository;
    }

    /**
     * Endpoint to retrieve all heroes.
     *
     * @return CollectionModel of EntityModel representing all heroes.
     */
    @GetMapping("/heroes")
    public CollectionModel<EntityModel<HeroModel>> all() {
        List<EntityModel<HeroModel>> heroes = heroRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(heroes);
    }

    /**
     * Endpoint to retrieve a single hero by ID.
     *
     * @param id The ID of the hero to retrieve.
     * @return EntityModel representing the requested hero.
     */
    @GetMapping("/hero/{id}")
    public EntityModel<HeroDTO> one(@PathVariable Long id) {
        HeroModel hero = heroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        HeroDTO heroDTO = convertToHeroDTO(hero);

        FactionModel faction = hero.getFaction();
        if (faction != null) {
            heroDTO.setFactionId(faction.getFactionId());
            heroDTO.setFactionName(faction.getFactionName());
        }

        return EntityModel.of(heroDTO);
    }

    /**
     * Endpoint to create a new hero.
     *
     * @param heroDTO The DTO representing the new hero.
     * @return ResponseEntity with status and URI of the new resource.
     */
    @PostMapping("/hero")
    public ResponseEntity<EntityModel<HeroModel>> newHero(@RequestBody HeroDTO heroDTO) {
        HeroModel newHero = convertToHeroModel(heroDTO);
        FactionModel faction = factionRepository.findById(heroDTO.getFactionId())
                .orElseThrow(() -> new NotFoundException(heroDTO.getFactionId()));
        newHero.setFaction(faction);
        HeroModel savedHero = heroRepository.save(newHero);
        return ResponseEntity.created(linkTo(methodOn(HeroController.class).one(savedHero.getHeroId())).toUri())
                .body(assembler.toModel(savedHero));
    }

    /**
     * Endpoint to update an existing hero.
     *
     * @param id             The ID of the hero to update.
     * @param updatedHeroDTO The DTO containing updated hero information.
     * @return ResponseEntity with status and updated hero representation.
     */
    @PutMapping("/hero/{id}")
    ResponseEntity<EntityModel<HeroModel>> updateHero(@PathVariable Long id, @RequestBody HeroDTO updatedHeroDTO) {
        HeroModel existingHero = heroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
        updateHeroFromDTO(existingHero, updatedHeroDTO);
        HeroModel savedHero = heroRepository.save(existingHero);
        return ResponseEntity.ok(assembler.toModel(savedHero));
    }

    /**
     * Endpoint to delete a hero by ID.
     *
     * @param id The ID of the hero to delete.
     * @return ResponseEntity with status indicating success or failure of deletion.
     */
    @DeleteMapping("/hero/{id}")
    ResponseEntity<?> deleteHero(@PathVariable Long id) {
        heroRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Utility method to convert HeroDTO to HeroModel.
     *
     * @param heroDTO The DTO representing the hero.
     * @return The corresponding HeroModel.
     */
    private HeroModel convertToHeroModel(HeroDTO heroDTO) {
        HeroModel hero = new HeroModel();
        hero.setHeroName(heroDTO.getHeroName());
        hero.setHeroType(heroDTO.getHeroType());
        hero.setUniqueHero(heroDTO.isUniqueHero());
        return hero;
    }

    /**
     * Utility method to update HeroModel from HeroDTO.
     *
     * @param hero     The existing HeroModel to be updated.
     * @param heroDTO  The DTO containing updated hero information.
     */
    private void updateHeroFromDTO(HeroModel hero, HeroDTO heroDTO) {
        hero.setHeroName(heroDTO.getHeroName());
        hero.setHeroType(heroDTO.getHeroType());
        hero.setUniqueHero(heroDTO.isUniqueHero());
    }

    /**
     * Utility method to convert HeroModel to HeroDTO.
     *
     * @param hero The HeroModel to be converted.
     * @return The corresponding HeroDTO.
     */
    private HeroDTO convertToHeroDTO(HeroModel hero) {
        HeroDTO heroDTO = new HeroDTO();
        heroDTO.setHeroId(hero.getHeroId());
        heroDTO.setHeroName(hero.getHeroName());
        heroDTO.setHeroType(hero.getHeroType());
        heroDTO.setUniqueHero(hero.getUniqueHero());
        FactionModel faction = hero.getFaction();
        if (faction != null) {
            heroDTO.setFactionId(faction.getFactionId());
            heroDTO.setFactionName(faction.getFactionName());
        }
        return heroDTO;
    }
}