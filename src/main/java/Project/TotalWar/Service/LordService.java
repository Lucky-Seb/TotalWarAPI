package Project.TotalWar.Service;

import Project.TotalWar.Model.LordModel;
import Project.TotalWar.Repository.LordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LordService {
    @Autowired
    private LordRepository lordRepository;

    public LordModel getLordWithRaceAndFaction(long lordId) {
        return lordRepository.findById(lordId);
    }
}