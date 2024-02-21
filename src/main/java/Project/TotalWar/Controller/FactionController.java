package Project.TotalWar.Controller;
import Project.TotalWar.Model.FactionModel;
import Project.TotalWar.Repository.FactionRepository;
import Project.TotalWar.util.FactionModelAssembler;
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
@RequestMapping("/factions")
public class FactionController {

    private final FactionRepository factionRepository;
    private final FactionModelAssembler assembler;

    FactionController(FactionRepository factionRepository, FactionModelAssembler assembler) {
        this.factionRepository = factionRepository;
        this.assembler = assembler;
    }

    @GetMapping("/factions")
    public CollectionModel<EntityModel<FactionModel>> all() {
        List<EntityModel<FactionModel>> factions = factionRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(factions,
                linkTo(methodOn(FactionController.class).all()).withSelfRel());
    }

    @GetMapping("/faction/{id}")
    public EntityModel<FactionModel> one(@PathVariable Long id) {
        FactionModel faction = factionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        return assembler.toModel(faction);
    }

    @PostMapping("/faction/")
    ResponseEntity<EntityModel<FactionModel>> newFaction(@RequestBody FactionModel faction) {
        FactionModel newFaction = factionRepository.save(faction);

        return ResponseEntity
                .created(linkTo(methodOn(FactionController.class).one(newFaction.getFactionId())).toUri())
                .body(assembler.toModel(newFaction));
    }

    @PutMapping("/faction/{id}")
    ResponseEntity<EntityModel<FactionModel>> updateFaction(@PathVariable Long id, @RequestBody FactionModel updatedFaction) {
        FactionModel existingFaction = factionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        // Update existingFaction with properties from updatedFaction
        existingFaction.setFactionName(updatedFaction.getFactionName());
        existingFaction.setHeroes(updatedFaction.getHeroes());
        // Add other properties as needed

        FactionModel savedFaction = factionRepository.save(existingFaction);

        return ResponseEntity.ok(assembler.toModel(savedFaction));
    }

    @DeleteMapping("/faction/{id}")
    ResponseEntity<?> deleteFaction(@PathVariable Long id) {
        factionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Add other methods as needed

    // ... (other methods)
}