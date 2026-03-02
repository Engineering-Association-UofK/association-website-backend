package edu.uofk.ea.association_website_backend.controller;

import edu.uofk.ea.association_website_backend.model.activity.ActivityType;
import edu.uofk.ea.association_website_backend.model.event.*;
import edu.uofk.ea.association_website_backend.service.ActivityService;
import edu.uofk.ea.association_website_backend.service.AdminDetailsService;
import edu.uofk.ea.association_website_backend.service.event.EventComponentService;
import edu.uofk.ea.association_website_backend.service.event.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;
    private final EventComponentService eventComponentService;
    private final ActivityService activityService;
    private final AdminDetailsService adminDetailsService;


    @Autowired
    public EventController(EventService eventService, EventComponentService eventComponentService, ActivityService activityService, AdminDetailsService adminDetailsService) {
        this.eventService = eventService;
        this.eventComponentService = eventComponentService;
        this.activityService = activityService;
        this.adminDetailsService = adminDetailsService;
    }

    @GetMapping
    public Iterable<EventListResponse> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public EventResponse getEventById(@PathVariable int id) {
        return eventService.getEventById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('EVENT_MANAGER', 'SUPER_ADMIN')")
    public void createEvent(@Valid @RequestBody EventRequest request, Authentication authentication) {
        eventService.makeEvent(request);
        int adminId = adminDetailsService.getId(authentication.getName());
        activityService.log(ActivityType.CREATE_EVENT, Map.of("name", request.getName()), adminId);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('EVENT_MANAGER', 'SUPER_ADMIN')")
    public void updateEvent(@Valid @RequestBody EventUpdateRequest request, Authentication authentication) {
        eventService.updateEvent(request);
        int adminId = adminDetailsService.getId(authentication.getName());
        activityService.log(ActivityType.UPDATE_EVENT, Map.of("id", request.getId(), "name", request.getName()), adminId);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('EVENT_MANAGER', 'SUPER_ADMIN')")
    public void deleteEvent(@PathVariable int id, Authentication authentication) {
        String eventName = eventService.getEventById(id).getName();
        eventService.deleteEvent(id);
        int adminId = adminDetailsService.getId(authentication.getName());
        activityService.log(ActivityType.DELETE_EVENT, Map.of("id", id, "name", eventName), adminId);
    }

    @PostMapping("/{eventId}/apply/{studentId}")
    public void applyForEvent(@PathVariable int eventId, @PathVariable int studentId) {
        eventService.apply(eventId, studentId);
    }

    @PostMapping("/{eventId}/mass-apply")
    @PreAuthorize("hasAnyRole('EVENT_MANAGER', 'SUPER_ADMIN')")
    public void massApplyForEvent(@PathVariable int eventId, @RequestBody List<Integer> studentIds) {
        eventService.massApply(eventId, studentIds);
    }

    @DeleteMapping("/{eventId}/remove-participant/{studentId}")
    @PreAuthorize("hasAnyRole('EVENT_MANAGER', 'SUPER_ADMIN')")
    public void removeParticipant(@PathVariable int eventId, @PathVariable int studentId, Authentication authentication) {
        eventService.removeParticipant(eventId, studentId);
        int adminId = adminDetailsService.getId(authentication.getName());
        activityService.log(ActivityType.DELETE_EVENT_PARTICIPANT, Map.of("studentId", studentId, "eventId", eventId), adminId);
    }

    @GetMapping("/{eventId}/participants")
    @PreAuthorize("hasAnyRole('EVENT_MANAGER', 'SUPER_ADMIN')")
    public List<ParticipantsResponse> getParticipants(@PathVariable int eventId) {
        return eventService.getParticipantsByEventId(eventId);
    }

    @PostMapping("/components")
    @PreAuthorize("hasAnyRole('EVENT_MANAGER', 'SUPER_ADMIN')")
    public void createEventComponent(@Valid @RequestBody EventComponentRequest request, Authentication authentication) {
        eventComponentService.make(request);
        int adminId = adminDetailsService.getId(authentication.getName());
        activityService.log(ActivityType.CREATE_EVENT_COMPONENT, Map.of("name", request.getName(), "eventId", request.getEventId()), adminId);
    }

    @GetMapping("/components/{id}")
    public EventComponentModel getEventComponentById(@PathVariable int id) {
        return eventComponentService.findById(id);
    }

    @GetMapping("/{eventId}/components")
    public Iterable<EventComponentModel> getEventComponentsByEventId(@PathVariable int eventId) {
        return eventComponentService.getByEventId(eventId);
    }

    @PutMapping("/components")
    @PreAuthorize("hasAnyRole('EVENT_MANAGER', 'SUPER_ADMIN')")
    public void updateEventComponent(@Valid @RequestBody EventComponentRequest request, Authentication authentication) {
        eventComponentService.update(request);
        int adminId = adminDetailsService.getId(authentication.getName());
        activityService.log(ActivityType.UPDATE_EVENT_COMPONENT, Map.of("id", request.getId(), "name", request.getName()), adminId);
    }

    @DeleteMapping("/components/{id}")
    @PreAuthorize("hasAnyRole('EVENT_MANAGER', 'SUPER_ADMIN')")
    public void deleteEventComponent(@PathVariable int id, Authentication authentication) {
        String componentName = eventComponentService.findById(id).getName();
        eventComponentService.delete(id);
        int adminId = adminDetailsService.getId(authentication.getName());
        activityService.log(ActivityType.DELETE_EVENT_COMPONENT, Map.of("id", id, "name", componentName), adminId);
    }

    @PostMapping("/scores")
    @PreAuthorize("hasAnyRole('EVENT_MANAGER', 'SUPER_ADMIN')")
    public void setStudentScore(@Valid @RequestBody SetScoreRequest request, Authentication authentication) {
        eventService.setStudentScoreOnComponent(request);
        int adminId = adminDetailsService.getId(authentication.getName());
        activityService.log(ActivityType.SET_SCORE, Map.of("eventId", request.getEventId(), "studentId", request.getStudentId(), "componentId", request.getComponentId(), "score", request.getScore()), adminId);
    }

    @PostMapping("/mass-scores")
    @PreAuthorize("hasAnyRole('EVENT_MANAGER', 'SUPER_ADMIN')")
    public void massSetStudentScore(@Valid @RequestBody MassSetScoreRequest request, Authentication authentication) {
        eventService.massSetStudentScoreOnComponent(request);
        int adminId = adminDetailsService.getId(authentication.getName());
        activityService.log(ActivityType.SET_SCORES, Map.of("eventId", request.getEventId(), "componentId", request.getComponentId()), adminId);
    }

}
