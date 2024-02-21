package Project.TotalWar.util;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import Project.TotalWar.Controller.HeroController;
import Project.TotalWar.Model.HeroModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class HeroModelAssembler implements RepresentationModelAssembler<HeroModel, EntityModel<HeroModel>> {

    @Override
    public EntityModel<HeroModel> toModel(HeroModel hero) {

        // Unconditional links to single-item resource and aggregate root

        EntityModel<HeroModel> heroModel = EntityModel.of(hero,
                linkTo(methodOn(HeroController.class).one(hero.getHeroId())).withSelfRel(),
                linkTo(methodOn(HeroController.class).all()).withRel("heroes"));

        // Add additional links as needed for HeroModel

        return heroModel;
    }
}