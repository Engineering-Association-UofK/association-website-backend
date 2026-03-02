package edu.uofk.ea.association_website_backend.model.event;

import jakarta.persistence.*;

@Entity
@Table(
        name = "student_component_scores",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"participant_id", "component_id"}
        )
)
public class StudentComponentScoreModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id", nullable = false)
    private EventParticipationModel participant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "component_id", nullable = false)
    private EventComponentModel component;

    @Column(name = "score")
    private Double score;

    public StudentComponentScoreModel() {}

    public StudentComponentScoreModel(EventParticipationModel participant,
                                      EventComponentModel component,
                                      Double score) {
        this.participant = participant;
        this.component = component;
        this.score = score;
    }

    public EventParticipationModel getParticipant() {
        return participant;
    }

    public void setParticipant(EventParticipationModel participant) {
        this.participant = participant;
    }

    public EventComponentModel getComponent() {
        return component;
    }

    public void setComponent(EventComponentModel component) {
        this.component = component;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
