package Project.TotalWar.mappers;

import Project.TotalWar.DTO.FactionDTO;
import Project.TotalWar.DTO.FactionWithHerosDTO;
import Project.TotalWar.DTO.HeroDTO;
import Project.TotalWar.Model.FactionModel;
import Project.TotalWar.Model.HeroModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface FactionMapper {

    @Mapping(source = "heroDTO", target = "hero")
    List<HeroModel> dtoToHeroList(List<HeroDTO> heroDTOs);

    void updateModel(FactionModel existingFaction, FactionDTO updatedFactionDTO);

    @Mapping(source = "factionId", target = "id")
    @Mapping(source = "heroes", target = "heroDTOs")
    FactionDTO toDTO(FactionModel faction);

    @Mapping(source = "id", target = "factionId")
    @Mapping(source = "heroDTOs", target = "heroes")
    FactionModel toModel(FactionDTO factionDTO);

    @Mapping(source = "factionId", target = "id")
    @Mapping(source = "factionName", target = "name") // Assuming a name property in FactionWithHeroesDTO
    @Mapping(source = "heroes", target = "heroes", qualifiedByName = "toDTOList")
    FactionWithHerosDTO toFactionWithHeroesDTO(FactionModel factionModel);

    @Named("toDTOList")
    List<HeroDTO> toDTOList(List<HeroModel> heroes);

    // Generic method for mapping collections
    @Mapping(source = "source", target = "target") // Example mapping
    <T, S> List<T> toList(List<S> sources, Class<T> targetClass);
}
