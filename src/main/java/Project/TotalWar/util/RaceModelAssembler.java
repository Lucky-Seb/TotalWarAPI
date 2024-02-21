package Project.TotalWar.util;

import Project.TotalWar.Controller.LordController;
import Project.TotalWar.Model.RaceModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RaceModelAssembler implements RepresentationModelAssembler<RaceModel, EntityModel<RaceModel>> {

    @Override
    public EntityModel<RaceModel> toModel(RaceModel race) {

        return EntityModel.of(race, //
                linkTo(methodOn(LordController.class).one(race.getRaceId())).withSelfRel(),
                linkTo(methodOn(LordController.class).all()).withRel("race"));
    }
}
