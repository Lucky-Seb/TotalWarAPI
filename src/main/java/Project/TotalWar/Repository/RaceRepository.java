package Project.TotalWar.Repository;

import Project.TotalWar.Model.LordModel;
import Project.TotalWar.Model.RaceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RaceRepository extends JpaRepository<RaceModel, Long> {
    RaceModel findById(long factionId);
}
