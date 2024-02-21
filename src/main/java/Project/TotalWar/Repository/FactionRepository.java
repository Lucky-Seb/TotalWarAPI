package Project.TotalWar.Repository;

import Project.TotalWar.Model.FactionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FactionRepository extends JpaRepository<FactionModel, Long> {
    FactionModel findById(long factionId);
}