package Project.TotalWar.util;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import Project.TotalWar.Controller.FactionController;
import Project.TotalWar.Model.FactionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class FactionModelAssembler implements RepresentationModelAssembler<FactionModel, EntityModel<FactionModel>> {

    @Override
    public EntityModel<FactionModel> toModel(FactionModel faction) {

        // Unconditional links to single-item resource and aggregate root

        EntityModel<FactionModel> factionModel = EntityModel.of(faction,
                linkTo(methodOn(FactionController.class).one(faction.getFactionId())).withSelfRel(),
                linkTo(methodOn(FactionController.class).all()).withRel("factions"));

        // Add additional links as needed for FactionModel

        return factionModel;
    }
}