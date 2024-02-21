package Project.TotalWar.util;

import Project.TotalWar.Controller.UnitController;
import Project.TotalWar.Model.UnitModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UnitModelAssembler implements RepresentationModelAssembler<UnitModel, EntityModel<UnitModel>> {

    @Override
    public EntityModel<UnitModel> toModel(UnitModel unit) {

        // Unconditional links to single-item resource and aggregate root

        EntityModel<UnitModel> unitModel = EntityModel.of(unit,
                linkTo(methodOn(UnitController.class).one(unit.getUnitId())).withSelfRel(),
                linkTo(methodOn(UnitController.class).all()).withRel("Units"));

        // Add additional links as needed for FactionModel

        return unitModel;
    }
}