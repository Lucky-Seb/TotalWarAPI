package Project.TotalWar.Repository;

import Project.TotalWar.Model.UnitModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitRepository extends JpaRepository<UnitModel, Long> {
    // You can add custom query methods if needed
}