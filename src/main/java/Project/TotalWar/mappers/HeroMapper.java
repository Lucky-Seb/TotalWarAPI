package Project.TotalWar.mappers;

import Project.TotalWar.DTO.HeroDTO;
import Project.TotalWar.Model.HeroModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HeroMapper {

    @Mapping(source = "faction.id", target = "factionId")
    @Mapping(source = "faction.name", target = "factionName") // Map faction name if available
    HeroDTO toDTO(HeroModel heroModel);

    @Mapping(source = "factionId", target = "faction.id")
    @Mapping(target = "faction", ignore = true) // Ignore faction to avoid infinite recursion
    HeroModel toModel(HeroDTO heroDTO);

    void updateModel(HeroModel existingHero, HeroDTO updatedHeroDTO);
}