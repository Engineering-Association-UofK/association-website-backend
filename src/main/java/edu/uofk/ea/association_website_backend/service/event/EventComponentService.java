package edu.uofk.ea.association_website_backend.service.event;

import edu.uofk.ea.association_website_backend.model.event.*;
import edu.uofk.ea.association_website_backend.model.student.StudentModel;
import edu.uofk.ea.association_website_backend.repository.StudentRepo;
import edu.uofk.ea.association_website_backend.repository.event.EventComponentRepo;
import edu.uofk.ea.association_website_backend.repository.event.EventParticipationRepo;
import edu.uofk.ea.association_website_backend.repository.event.EventRepo;
import edu.uofk.ea.association_website_backend.repository.event.StudentComponentScoreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class EventComponentService {

    private final EventComponentRepo eventComponentRepo;
    private final EventRepo eventRepo;

    @Autowired
    public EventComponentService(EventComponentRepo eventComponentRepo, EventRepo eventRepo) {
        this.eventComponentRepo = eventComponentRepo;
        this.eventRepo = eventRepo;
    }

    @Transactional
    public void make(EventComponentRequest request) {
        var event = eventRepo.findById(request.getEventId());
        if (event == null) throw new IllegalArgumentException("Event not found");
        if (event.getEndDate().isBefore(LocalDate.now()))
            throw new IllegalArgumentException("Event Updating not allowed after end date");

        EventComponentModel eventComponent = new EventComponentModel(
                request.getEventId(),
                request.getName(),
                request.getMaxScore()
        );
        eventComponentRepo.save(eventComponent);
    }

    public EventComponentModel findById(int id){
        EventComponentModel model = eventComponentRepo.findById(id);
        if (model == null) throw new IllegalArgumentException("Event component not found");
        return model;
    }

    public Iterable<EventComponentModel> getByEventId(int eventId){
        return eventComponentRepo.getByEventId(eventId);
    }

    @Transactional
    public void update(EventComponentRequest request){
        var event = eventRepo.findById(request.getEventId());
        if (event == null) throw new IllegalArgumentException("Event not found");
        if (event.getEndDate().isBefore(LocalDate.now()))
            throw new IllegalArgumentException("Event Updating not allowed after end date");

        EventComponentModel eventComponent = eventComponentRepo.findById(request.getId());
        if (eventComponent == null) throw new IllegalArgumentException("Event component not found");

        eventComponent.setEventId(request.getEventId());
        eventComponent.setName(request.getName());
        eventComponent.setMaxScore(request.getMaxScore());

        eventComponentRepo.update(eventComponent);
    }

    @Transactional
    public void delete(int id){
        var event = eventRepo.findById(eventComponentRepo.findById(id).getEventId());
        if (event == null) throw new IllegalArgumentException("Event not found");
        if (event.getEndDate().isBefore(LocalDate.now()))
            throw new IllegalArgumentException("Event Updating not allowed after end date");

        EventComponentModel eventComponent = eventComponentRepo.findById(id);
        if (eventComponent == null) throw new IllegalArgumentException("Event component not found");

        eventComponentRepo.delete(id);
    }
}
