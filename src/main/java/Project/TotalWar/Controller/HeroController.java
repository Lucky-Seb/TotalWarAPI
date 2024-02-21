package Project.TotalWar.Controller;

import Project.TotalWar.Model.HeroModel;
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

    HeroController(HeroRepository heroRepository, HeroModelAssembler assembler) {
        this.heroRepository = heroRepository;
        this.assembler = assembler;
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
    public EntityModel<HeroModel> one(@PathVariable Long id) {
        HeroModel hero = heroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        return assembler.toModel(hero);
    }

    @PostMapping("/hero")
    ResponseEntity<EntityModel<HeroModel>> newHero(@RequestBody HeroModel hero) {
        // Let the entity handle conversion from int to boolean
        HeroModel newHero = heroRepository.save(hero);

        return ResponseEntity
                .created(linkTo(methodOn(HeroController.class).one(newHero.getHeroId())).toUri())
                .body(assembler.toModel(newHero));
    }

    @PutMapping("/hero/{id}")
    ResponseEntity<EntityModel<HeroModel>> updateHero(@PathVariable Long id, @RequestBody HeroModel updatedHero) {
        HeroModel existingHero = heroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        // Update existingHero with properties from updatedHero
        existingHero.setHeroName(updatedHero.getHeroName());
        existingHero.setHeroType(updatedHero.getHeroType());
        existingHero.setUniqueHero(updatedHero.getUniqueHero());
        existingHero.setFaction(updatedHero.getFaction());
        existingHero.setRace(updatedHero.getRace());
        // Add other properties as needed

        HeroModel savedHero = heroRepository.save(existingHero);

        return ResponseEntity.ok(assembler.toModel(savedHero));
    }

    @DeleteMapping("/hero/{id}")
    ResponseEntity<?> deleteHero(@PathVariable Long id) {
        heroRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}