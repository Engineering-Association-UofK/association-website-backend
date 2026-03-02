package edu.uofk.ea.association_website_backend.model.event;

import jakarta.persistence.*;

@Entity
@Table(name = "event_participation")
public class EventParticipationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "event_id")
    private int eventId;

    @Column(name = "student_id")
    private int studentId;

    public EventParticipationModel() {}

    public EventParticipationModel(int eventId, int studentId) {
        this.eventId = eventId;
        this.studentId = studentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
}
