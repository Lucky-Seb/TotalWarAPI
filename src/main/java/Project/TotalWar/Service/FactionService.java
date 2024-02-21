package Project.TotalWar.Service;

import Project.TotalWar.Model.FactionModel;
import Project.TotalWar.Repository.FactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;



@Service
public class FactionService {
    @Autowired
    private FactionRepository factionRepository;

    public FactionModel getFactionWithHeroes(long factionId) {
        return factionRepository.findById(factionId);
    }
}