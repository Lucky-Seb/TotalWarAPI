package Project.TotalWar.util;

import Project.TotalWar.Controller.LordController;
import Project.TotalWar.Model.LordModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RaceModelAssembler implements RepresentationModelAssembler<LordModel, EntityModel<LordModel>> {

    @Override
    public EntityModel<LordModel> toModel(LordModel lord) {

        return EntityModel.of(lord, //
                linkTo(methodOn(LordController.class).one(lord.getLordId())).withSelfRel(),
                linkTo(methodOn(LordController.class).all()).withRel("lord"));
    }
}