package Project.TotalWar.Repository;

import Project.TotalWar.Model.HeroModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HeroRepository extends JpaRepository<HeroModel, Long> {
    @Query("SELECT h FROM HeroModel h WHERE h.faction.factionId = :factionId")
    List<HeroModel> findByFactionId(Long factionId);
}