package Project.TotalWar.Controller;

import Project.TotalWar.Model.UnitModel;
import Project.TotalWar.Repository.UnitRepository;
import Project.TotalWar.util.UnitModelAssembler;
import Project.TotalWar.util.NotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UnitController {

    private final UnitRepository unitRepository;
    private final UnitModelAssembler assembler;

    UnitController(UnitRepository unitRepository, UnitModelAssembler assembler) {
        this.unitRepository = unitRepository;
        this.assembler = assembler;
    }

    @GetMapping("/units")
    public CollectionModel<EntityModel<UnitModel>> all() {
        List<EntityModel<UnitModel>> units = unitRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(units,
                linkTo(methodOn(UnitController.class).all()).withSelfRel());
    }

    @GetMapping("/unit/{id}")
    public EntityModel<UnitModel> one(@PathVariable Long id) {
        UnitModel unit = unitRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        return assembler.toModel(unit);
    }

    @PostMapping("/unit/")
    public ResponseEntity<EntityModel<UnitModel>> newUnit(@RequestBody UnitModel unit) {
        UnitModel newUnit = unitRepository.save(unit);

        return ResponseEntity
                .created(linkTo(methodOn(UnitController.class).one(newUnit.getUnitId())).toUri())
                .body(assembler.toModel(newUnit));
    }

    @PutMapping("/unit/{id}")
    public ResponseEntity<EntityModel<UnitModel>> updateUnit(@PathVariable Long id, @RequestBody UnitModel updatedUnit) {
        UnitModel existingUnit = unitRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        // Update existingUnit with properties from updatedUnit
        existingUnit.setUnitName(updatedUnit.getUnitName());
        existingUnit.setUnitCost(updatedUnit.getUnitCost());
        existingUnit.setUnitTier(updatedUnit.getUnitTier());
        existingUnit.setFaction(updatedUnit.getFaction());
        existingUnit.setRace(updatedUnit.getRace());
        // Add other properties as needed

        UnitModel savedUnit = unitRepository.save(existingUnit);

        return ResponseEntity.ok(assembler.toModel(savedUnit));
    }

    @DeleteMapping("/unit/{id}")
    public ResponseEntity<?> deleteUnit(@PathVariable Long id) {
        unitRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Add other methods as needed

    // ... (other methods)
}