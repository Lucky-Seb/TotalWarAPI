package Project.TotalWar.Repository;

import Project.TotalWar.Model.FactionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.hateoas.LinkRelation;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FactionRepository extends JpaRepository<FactionModel, Long> {
    // Custom query to eagerly fetch factions along with their associated heroes
    @Query("SELECT DISTINCT f FROM FactionModel f LEFT JOIN FETCH f.heroes")
    List<FactionModel> findAllWithHeroes();

    List<FactionModel> findAllWithEagerRelationships();

    Optional<FactionModel> findByIdWithEagerRelationships(Long id);
}
