package Project.TotalWar.Repository;

import Project.TotalWar.Model.UnitModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitRepository extends JpaRepository<UnitModel, Long> {
    // You can add custom query methods if needed
}