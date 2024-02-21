package Project.TotalWar.Controller;

import java.util.List;
import java.util.stream.Collectors;

import Project.TotalWar.Model.LordModel;
import Project.TotalWar.Repository.LordRepository;
import Project.TotalWar.util.LordModelAssembler;
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
public class UnitController {
    private final LordRepository repository;
    private final LordModelAssembler assembler;

    LordController(LordRepository repository, LordModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/lords")
    public CollectionModel<EntityModel<LordModel>> all() {

        List<EntityModel<LordModel>> lords = repository.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(lords, linkTo(methodOn(LordController.class).all()).withSelfRel());
    }
    // end::get-aggregate-root[]

    @PostMapping("/lord")
    LordModel newLord(@RequestBody LordModel newLord) {
        return repository.save(newLord);
    }

    // Single item

    @GetMapping("/lord/{id}")
    public EntityModel<LordModel> one(@PathVariable Long id) {

        LordModel lord = repository.findById(id) //
                .orElseThrow(() -> new NotFoundException(id));

        return assembler.toModel(lord);
    }

    @PutMapping("/lord/{id}")
    LordModel replaceLord(@RequestBody LordModel newLord, @PathVariable Long id) {

        return repository.findById(id)
                .map(lord -> {
                    lord.setLordName(newLord.getLordName());
                    lord.setFaction(newLord.getFaction());
                    lord.setFactionLeader(newLord.getFactionLeader());
                    lord.setRace(newLord.getRace());

                    return repository.save(lord);
                })
                .orElseGet(() -> {
                    newLord.setLordId(id);
                    return repository.save(newLord);
                });
    }

    @DeleteMapping("/lord/{id}")
    void deleteEmployee(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
