package Project.TotalWar.util;

public class NotFoundException extends RuntimeException{
    NotFoundException(Long id) {
        super("Could not Item " + id);
    }
}
