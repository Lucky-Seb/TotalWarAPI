package Project.TotalWar.util;

public class NotFoundException extends RuntimeException{
    public NotFoundException(Long id) {
        super("Could not Item " + id);
    }
}
