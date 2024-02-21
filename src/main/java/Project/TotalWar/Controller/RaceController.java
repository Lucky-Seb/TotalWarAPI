package Project.TotalWar.Controller;

import java.util.List;
import java.util.stream.Collectors;

import Project.TotalWar.Model.RaceModel;
import Project.TotalWar.Repository.RaceRepository;
import Project.TotalWar.util.RaceModelAssembler;
import Project.TotalWar.util.NotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class RaceController {
    private final RaceRepository repository;
    private final RaceModelAssembler assembler;

    RaceController(RaceRepository repository, RaceModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    // Aggregate root
    @GetMapping("/races")
    public CollectionModel<EntityModel<RaceModel>> all() {

        List<EntityModel<RaceModel>> races = repository.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(races, linkTo(methodOn(RaceController.class).all()).withSelfRel());
    }

    @PostMapping("/race")
    RaceModel newRace(@RequestBody RaceModel newRace) {
        return repository.save(newRace);
    }

    // Single item
    @GetMapping("/race/{id}")
    public EntityModel<RaceModel> one(@PathVariable Long id) {

        RaceModel race = repository.findById(id) //
                .orElseThrow(() -> new NotFoundException(id));

        return assembler.toModel(race);
    }

    @PutMapping("/race/{id}")
    RaceModel replaceRace(@RequestBody RaceModel newRace, @PathVariable Long id) {

        return repository.findById(id)
                .map(race -> {
                    race.setRaceName(newRace.getRaceName());
                    race.setFaction(newRace.getFaction());
                    race.setHero(newRace.getHero());

                    return repository.save(race);
                })
                .orElseGet(() -> {
                    newRace.setRaceId(id);
                    return repository.save(newRace);
                });
    }

    @DeleteMapping("/race/{id}")
    void deleteRace(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
