package Project.TotalWar.Repository;

import Project.TotalWar.Model.LordModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LordRepository extends JpaRepository<LordModel, Long> {
    LordModel findById(long factionId);
}
