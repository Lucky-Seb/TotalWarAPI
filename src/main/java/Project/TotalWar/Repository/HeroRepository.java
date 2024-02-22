package Project.TotalWar.Repository;

import Project.TotalWar.Model.HeroModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeroRepository extends JpaRepository<HeroModel, Long> {
    List<HeroModel> findByFactionFactionId(Long factionId);
    @Query("SELECT h FROM HeroModel h JOIN FETCH h.faction WHERE h.faction.factionId = :factionId")
    List<HeroModel> findByFactionId(@Param("factionId") Long factionId);
}
