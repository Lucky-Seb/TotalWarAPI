package Project.TotalWar.Model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Lord")
public class LordModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lord_id")
    private String lordId;

    @Column(name = "lord_name")
    private String lordName;

    @Column(name = "is_faction_leader")
    private Boolean isFactionLeader;

    @OneToOne
    @JoinColumn(name = "race_id")
    private RaceModel race;

    @ManyToOne
    @JoinColumn(name = "faction_id")
    private FactionModel faction;

    // Constructors, getters, and setters
    // (omitted for brevity)

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        LordModel other = (LordModel) obj;
        return Objects.equals(lordId, other.lordId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lordId);
    }

    @Override
    public String toString() {
        return "LordModel{" +
                "lordId='" + lordId + '\'' +
                ", lordName='" + lordName + '\'' +
                ", isFactionLeader=" + isFactionLeader +
                ", race=" + race +
                ", faction=" + faction +
                '}';
    }
}