package Project.TotalWar.Repository;

import Project.TotalWar.Model.HeroModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeroRepository extends JpaRepository<HeroModel, Long> {
    // You can add custom query methods if needed
}