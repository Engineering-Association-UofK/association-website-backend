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
                request.getEndDate(),
                request.getOutcomes()
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
        event.setOutcomes(request.getOutcomes());
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
        if (eventParticipationRepo.findByEventIdAndStudentIds(eventId, studentId) != null)
            throw new IllegalArgumentException("Student already applied for this event");

        EventModel event = eventRepo.findById(eventId);
        if (event == null) throw new IllegalArgumentException("Event not found");
        if (event.getStartDate().isBefore(LocalDate.now()) || event.getStartDate().isEqual(LocalDate.now()))
            throw new UnauthorizedException("Event application is closed");

        // Check if event is full
        if (event.getMaxParticipants() != 0 && event.getMaxParticipants() < eventParticipationRepo.getByEventId(eventId).size())
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
        EventParticipationModel participation = eventParticipationRepo.findByEventIdAndStudentIds(eventId, studentId);
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

        var participant = eventParticipationRepo.findByEventIdAndStudentIds(request.getEventId(), request.getStudentId());
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
        EventModel event = eventRepo.findById(eventId);
        if (event == null) throw new IllegalArgumentException("Event not found");

        Mapping data = new Mapping(eventId);

        return data.participants.stream().map(p -> {
            StudentModel student = data.studentMap.get(p.getStudentId());
            return new ParticipantsResponse(
                    p.getId(),
                    p.getEventId(),
                    p.getStudentId(),
                    student.getNameAr(),
                    student.getNameEn(),
                    student.getEmail(),
                    data.participantComponentScoreMap.getOrDefault(p.getId(), null),
                    data.getPercentage(p.getId())
            );
        }).toList();
    }

    public EventCertificateDetails getCertDetails(int eventId, int studentId) {
        EventModel event = eventRepo.findById(eventId);
        if (event == null) throw new IllegalArgumentException("Event not found");
        StudentModel student = studentRepo.findById(studentId);
        if (student == null) throw new IllegalArgumentException("Student not found");
        double percentageGrade = -1.0;

        List<EventComponentModel> components = eventComponentRepo.getByEventId(eventId);
        // If event has components, get their scores
        if (!components.isEmpty()) {
            // Get ids for each component
            List<Integer> componentIds = components.stream().map(EventComponentModel::getId).toList();

            // Get participantId
            int participant = eventParticipationRepo.findByEventIdAndStudentIds(eventId, studentId).getId();

            List<StudentComponentScoreModel> componentScoreModels = studentComponentScoreRepo.findByParticipantAndComponentIds(participant, componentIds);

            double scoreSum = componentScoreModels.stream().mapToDouble(StudentComponentScoreModel::getScore).sum();
            double maxScore = components.stream().mapToDouble(EventComponentModel::getMaxScore).sum();
            percentageGrade = maxScore > 0 ? (scoreSum / maxScore) * 100 : 0.0;
        }

        return new EventCertificateDetails(
                event.getName(),
                event.getEventType(),
                event.getOutcomes(),
                event.getStartDate(),
                event.getEndDate(),
                student.getNameAr(),
                student.getNameEn(),
                percentageGrade
        );
    }

    public EventMassCertificateDetails getAllDetails(List<Integer> studentIds, int eventId) {
        EventModel event = eventRepo.findById(eventId);
        if (event == null) throw new IllegalArgumentException("Event not found");

        Mapping data = new Mapping(eventId, studentIds);

        return new EventMassCertificateDetails(
                event.getName(),
                event.getEventType(),
                event.getOutcomes(),
                event.getStartDate(),
                event.getEndDate(),
                data.studentNameAr,
                data.studentNameEn,
                data.percentageGrade
        );
    }

    private class Mapping {
        int eventId;

        List<StudentModel> eventStudents;
        List<EventParticipationModel> participants;
        List<EventComponentModel> eventComponents;

        List<Integer> eventStudentIds;
        List<Integer> participantsIds;
        List<Integer> eventComponentIds;

        Map<Integer, String> studentNameAr;
        Map<Integer, String> studentNameEn;
        Map<Integer, Double> percentageGrade;

        Map<Integer, StudentModel> studentMap;
        Map<Integer, Map<String, Double>> participantComponentScoreMap;

        double scoreMaxSum;

        public Mapping(int eventId) {
            this.eventId = eventId;
            participants = eventParticipationRepo.getByEventId(eventId);

            // Get participants for the event
            eventStudentIds = participants.stream().map(EventParticipationModel::getStudentId).toList();
            participantsIds = participants.stream().map(EventParticipationModel::getId).toList();
            eventStudents = studentRepo.findAllById(eventStudentIds);

            setUp();
        }

        public Mapping(int eventId, List<Integer> eventStudentIds) {
            this.eventId = eventId;

            eventStudents = studentRepo.findAllById(eventStudentIds);
            if (eventStudents.size() != eventStudentIds.size()) throw new IllegalArgumentException("Some students not found");
            this.eventStudentIds = eventStudentIds;

            participants = eventParticipationRepo.findByEventIdAndStudentIds(eventId, eventStudentIds);
            if (participants.size() != eventStudentIds.size()) throw new IllegalArgumentException("Some students are not participants on this event");
            participantsIds = participants.stream().map(EventParticipationModel::getId).toList();

            setUp();
            mapStudentsData();
        }

        private void setUp() {
            studentMap = eventStudents.stream().collect(Collectors.toMap(StudentModel::getId, s -> s));
            eventComponents = eventComponentRepo.getByEventId(eventId);
            if (!eventComponents.isEmpty()) {
                // Get ids for each component
                eventComponentIds = eventComponents.stream().map(EventComponentModel::getId).toList();
                // Get scores for each participant and component
                participantComponentScoreMap = studentComponentScoreRepo.findByParticipantAndComponentIds(participantsIds, eventComponentIds);
            }
            scoreMaxSum = eventComponents.stream().mapToDouble(EventComponentModel::getMaxScore).sum();
        }

        private void mapStudentsData() {
            studentNameAr = eventStudents.stream().collect(Collectors.toMap(StudentModel::getId, StudentModel::getNameAr));
            studentNameEn = eventStudents.stream().collect(Collectors.toMap(StudentModel::getId, StudentModel::getNameEn));
            percentageGrade = participants.stream().collect(Collectors.toMap(EventParticipationModel::getStudentId, p -> getPercentage(p.getId())));
        }

        public double getPercentage(int participantId) {
            if (eventComponents == null) return -1.0;

            // Get scores for each component
            var studentScoreMap = participantComponentScoreMap.getOrDefault(participantId, Map.of());
            if (studentScoreMap.isEmpty())
                return 0.0;

            // Fill missing components with 0
            if (studentScoreMap.size() != eventComponents.size()) {
                for (EventComponentModel component : eventComponents) {
                    if (!studentScoreMap.containsKey(component.getName())) {
                        studentScoreMap.put(component.getName(), 0.0);
                    }
                }
            }

            // Add scores and calculate percentage of their sum
            double scores = studentScoreMap.values().stream().mapToDouble(Double::doubleValue).sum();
            double maxScore = eventComponents.stream().mapToDouble(EventComponentModel::getMaxScore).sum();
            return maxScore > 0 ? (scores / maxScore) * 100 : 0.0;
        }

    }
}
