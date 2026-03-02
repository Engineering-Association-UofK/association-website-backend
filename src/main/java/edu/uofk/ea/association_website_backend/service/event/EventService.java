package edu.uofk.ea.association_website_backend.service.event;

import edu.uofk.ea.association_website_backend.exceptionHandlers.exceptions.UnauthorizedException;
import edu.uofk.ea.association_website_backend.model.event.*;
import edu.uofk.ea.association_website_backend.model.student.*;
import edu.uofk.ea.association_website_backend.repository.StudentRepo;
import edu.uofk.ea.association_website_backend.repository.event.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EventService {

    private static final Logger log = LoggerFactory.getLogger(EventService.class);
    private final EventRepo eventRepo;
    private final EventComponentRepo eventComponentRepo;
    private final EventParticipationRepo eventParticipationRepo;
    private final StudentRepo studentRepo;
    private final StudentComponentScoreRepo studentComponentScoreRepo;

    @Autowired
    public EventService(EventRepo eventRepo, EventComponentRepo eventComponentRepo, EventParticipationRepo eventParticipationRepo, StudentRepo studentRepo, StudentComponentScoreRepo studentComponentScoreRepo) {
        this.eventRepo = eventRepo;
        this.eventComponentRepo = eventComponentRepo;
        this.eventParticipationRepo = eventParticipationRepo;
        this.studentRepo = studentRepo;
        this.studentComponentScoreRepo = studentComponentScoreRepo;
    }

    public Iterable<EventListResponse> getAllEvents() {
        return eventRepo.findAll().stream().map(EventListResponse::new).toList();
    }

    public EventResponse getEventById(int id) {
        EventModel event = eventRepo.findById(id);
        if (event == null) throw new IllegalArgumentException("Event not found");
        var participants = eventParticipationRepo.getByEventId(id);

        return new EventResponse(
                event.getId(),
                event.getName(),
                event.getEventType(),
                event.getMaxParticipants(),
                event.getStartDate().toString(),
                event.getEndDate().toString(),
                participants.size(),
                participants.stream().map(EventParticipationModel::getStudentId).toList()
        );
    }

    @Transactional
    public void makeEvent(EventRequest request) {
        EventModel event = new EventModel(
                request.getName(),
                request.getEventType(),
                request.getMaxParticipants(),
                request.getStartDate(),
                request.getEndDate()
        );
        eventRepo.save(event);
    }

    @Transactional
    public void updateEvent(EventUpdateRequest request) {
        EventModel event = eventRepo.findById(request.getId());
        if (event == null) throw new IllegalArgumentException("Event not found");

        if (event.getEndDate().isBefore(LocalDate.now()))
            throw new UnauthorizedException("Event Updating not allowed after end date");

        event.setName(request.getName());
        event.setEventType(request.getEventType());
        event.setMaxParticipants(request.getMaxParticipants());
        event.setStartDate(request.getStartDate());
        event.setEndDate(request.getEndDate());

        eventRepo.update(event);
    }

    @Transactional
    public void deleteEvent(int id) {
        EventModel event = eventRepo.findById(id);
        if (event == null) throw new IllegalArgumentException("Event not found");

        if (event.getEndDate().isBefore(LocalDate.now()))
            throw new UnauthorizedException("Event Deletion not allowed after end date");

        eventRepo.delete(id);
    }

    @Transactional
    public void apply(int eventId, int studentId) {
        // Check if student already applied for this event
        if (eventParticipationRepo.findByEventIdAndStudentId(eventId, studentId) != null)
            throw new IllegalArgumentException("Student already applied for this event");

        EventModel event = eventRepo.findById(eventId);
        if (event == null) throw new IllegalArgumentException("Event not found");
        if (event.getStartDate().isBefore(LocalDate.now()) || event.getStartDate().isEqual(LocalDate.now()))
            throw new UnauthorizedException("Event application is closed");

        // Check if event is full
        if (event.getMaxParticipants() != 0 && event.getMaxParticipants() <= eventParticipationRepo.getByEventId(eventId).size())
            throw new IllegalArgumentException("Event is full");

        StudentModel student = studentRepo.findById(studentId);
        if (student == null) throw new IllegalArgumentException("student not found");

        EventParticipationModel eventParticipation = new EventParticipationModel(eventId, studentId);
        eventParticipationRepo.save(eventParticipation);
    }

    @Transactional
    public void massApply(int eventId, List<Integer> studentIds) {
        EventModel event = eventRepo.findById(eventId);
        if (event == null) throw new IllegalArgumentException("Event not found");
        if (event.getStartDate().isBefore(LocalDate.now()) || event.getStartDate().isEqual(LocalDate.now()))
            throw new UnauthorizedException("Event application is closed");

        List<StudentModel> students = studentRepo.findAllById(studentIds);
        if (students.size() != studentIds.size()) throw new IllegalArgumentException("Some students not found");

        // Check if event is full
        if (event.getMaxParticipants() != 0 && event.getMaxParticipants() <= eventParticipationRepo.getByEventId(eventId).size() + students.size())
            throw new IllegalArgumentException("Event does not have enough space for all applicants");

        List<EventParticipationModel> eventParticipants =
                students.stream().map(s -> new EventParticipationModel(eventId, s.getId())).toList();
        eventParticipationRepo.saveAll(eventParticipants);
    }

    @Transactional
    public void removeParticipant(int eventId, int studentId) {
        EventParticipationModel participation = eventParticipationRepo.findByEventIdAndStudentId(eventId, studentId);
        if (participation == null) {
            throw new IllegalArgumentException("Student is not registered for this event");
        }
        eventParticipationRepo.delete(participation.getId());
    }

    @Transactional
    public void setStudentScoreOnComponent(SetScoreRequest request) {
        if (studentRepo.findById(request.getStudentId()) == null) throw new IllegalArgumentException("Student not found");

        var event = eventRepo.findById(request.getEventId());
        if (event == null) throw new IllegalArgumentException("Event not found");
        if (event.getEndDate().isBefore(LocalDate.now()))
            throw new UnauthorizedException("Event Updating not allowed after end date");

        var participant = eventParticipationRepo.findByEventIdAndStudentId(request.getEventId(), request.getStudentId());
        if (participant == null) throw new UnauthorizedException("Student not a participant");

        var eventComponent = eventComponentRepo.findById(request.getComponentId());
        if (eventComponent == null) throw new IllegalArgumentException("Component not found");
        if (eventComponent.getEventId() != event.getId()) throw new IllegalArgumentException("Component does not belong to this event");

        var studentComponentScore = studentComponentScoreRepo.findByParticipantAndComponentIds(participant.getId(), request.getComponentId());
        if (studentComponentScore == null) {
            studentComponentScore = new StudentComponentScoreModel(participant, eventComponent, request.getScore());
            studentComponentScoreRepo.save(studentComponentScore);
            return;
        }
        studentComponentScore.setScore(request.getScore());
        studentComponentScoreRepo.update(studentComponentScore);
    }

    @Transactional
    public void massSetStudentScoreOnComponent(MassSetScoreRequest request) {
        List<Integer> studentIdsAsInt = request.getStudentScores().keySet().stream().map(Integer::parseInt).toList();
        List<StudentModel> students = studentRepo.findAllById(studentIdsAsInt);
        if (students.size() != request.getStudentScores().size()) throw new IllegalArgumentException("Some students not found");

        EventModel event = eventRepo.findById(request.getEventId());
        if (event == null) throw new IllegalArgumentException("Event not found");
        if (event.getEndDate().isBefore(LocalDate.now()))
            throw new UnauthorizedException("Event Updating not allowed after end date");

        List<EventParticipationModel> participant = eventParticipationRepo.findByEventIdAndStudentIds(request.getEventId(), studentIdsAsInt);
        if (students.size() != participant.size()) throw new IllegalArgumentException("Some students are not participants on this event");

        EventComponentModel eventComponent = eventComponentRepo.findById(request.getComponentId());
        if (eventComponent == null) throw new IllegalArgumentException("Component not found");
        if (eventComponent.getEventId() != event.getId()) throw new IllegalArgumentException("Component does not belong to this event");

        List<StudentComponentScoreModel> studentComponentScore = studentComponentScoreRepo.findByParticipantAndComponentIds(participant.stream().map(EventParticipationModel::getId).toList(), eventComponent.getId());

        // Collect new scores to create
        Set<Integer> existingParticipantIds = studentComponentScore.stream()
                .map(scs -> scs.getParticipant().getId())
                .collect(Collectors.toSet());
        List<EventParticipationModel> newEntries = participant.stream()
                .filter(p -> !existingParticipantIds.contains(p.getId()))
                .toList();

        // Update existing scores
        studentComponentScore.forEach(scs -> {
            Double newScore = request.getStudentScores().get(String.valueOf(scs.getParticipant().getStudentId()));
            if (newScore != null) {
                scs.setScore(newScore);
                studentComponentScoreRepo.update(scs);
            }
        });

        // Create new score entries
        newEntries.forEach(p -> {
            Double score = request.getStudentScores().get(String.valueOf(p.getStudentId()));
            if (score != null) {
                StudentComponentScoreModel newScore = new StudentComponentScoreModel(p, eventComponent, score);
                studentComponentScoreRepo.save(newScore);
            }
        });

    }

    public List<ParticipantsResponse> getParticipantsByEventId(int eventId) {
        // Get the event
        EventModel event = eventRepo.findById(eventId);
        if (event == null) throw new IllegalArgumentException("Event not found");

        // Get its participants
        List<EventParticipationModel> participants = eventParticipationRepo.getByEventId(eventId);
        List<Integer> participantsIds = participants.stream().map(EventParticipationModel::getId).toList();

        // Get students for each participant and convert to map for faster lookup
        List<Integer> studentIds = participants.stream().map(EventParticipationModel::getStudentId).toList();
        List<StudentModel> students = studentRepo.findAllById(studentIds);
        Map<Integer, StudentModel> studentMap = students.stream().collect(Collectors.toMap(StudentModel::getId, s -> s));

        // Get its components
        List<EventComponentModel> components = eventComponentRepo.getByEventId(eventId);
        if (!components.isEmpty()) {
            // Get ids for each component
            List<Integer> componentIds = components.stream().map(EventComponentModel::getId).toList();
            // Get scores for each participant and component
            Map<Integer, List<StudentComponentScoreModel>> componentScoreMap = studentComponentScoreRepo.findByParticipantAndComponentIds(participantsIds, componentIds);

            return participants.stream().map(p -> {
                // Get scores for each component
                List<StudentComponentScoreModel> componentScoreModels = componentScoreMap.getOrDefault(p.getId(), List.of());
                // Convert to map
                var studentScoreMap = componentScoreModels.stream().collect(
                        Collectors.toMap(sc -> sc.getComponent().getName(),
                                StudentComponentScoreModel::getScore
                        )
                );
                // Fill missing components with 0
                if (studentScoreMap.size() != components.size()) {
                    for (EventComponentModel component : components) {
                        if (!studentScoreMap.containsKey(component.getName())) {
                            studentScoreMap.put(component.getName(), 0.0);
                        }
                    }
                }
                // Add scores and calculate average
                Double score = studentScoreMap.values().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);

                StudentModel student = studentMap.get(p.getStudentId());
                if (student == null) {
                    log.error("Student not found for participant {} in event {}. Found while collecting participants with scores.", p.getId(), eventId);
                    return null;
                }
                return new ParticipantsResponse(
                        p.getId(),
                        p.getEventId(),
                        p.getStudentId(),
                        student.getNameAr(),
                        student.getNameEn(),
                        student.getEmail(),
                        studentScoreMap,
                        score
                );
            }).toList();
        } else {
            return participants.stream().map(p -> {
                StudentModel student = studentMap.get(p.getStudentId());
                if (student == null) {
                    log.error("Student not found for participant {} in event {}. Found while collecting participants without scores.", p.getId(), eventId);
                    return null;
                }
                return new ParticipantsResponse(
                        p.getId(),
                        p.getEventId(),
                        p.getStudentId(),
                        student.getNameAr(),
                        student.getNameEn(),
                        student.getEmail(),
                        null,
                        0.0
                );
            }).toList();
        }
    }
}
