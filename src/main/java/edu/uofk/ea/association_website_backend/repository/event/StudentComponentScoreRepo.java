package edu.uofk.ea.association_website_backend.repository.event;

import edu.uofk.ea.association_website_backend.model.event.StudentComponentScoreModel;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class StudentComponentScoreRepo {

    private final EntityManager em;

    public StudentComponentScoreRepo(EntityManager em) {
        this.em = em;
    }

    public void save(StudentComponentScoreModel event) {
        em.persist(event);
    }

    public StudentComponentScoreModel findByParticipantAndComponentIds(int participantId, int componentId) {
        return em.createQuery("SELECT scs FROM StudentComponentScoreModel scs WHERE scs.participant.id = :participantId AND scs.component.id = :componentId", StudentComponentScoreModel.class)
                .setParameter("participantId", participantId)
                .setParameter("componentId", componentId)
                .getResultList().stream().findFirst().orElse(null);
    }

    public List<StudentComponentScoreModel> findByParticipantAndComponentIds(int participantId, List<Integer> componentIds) {
        return em.createQuery("SELECT scs FROM StudentComponentScoreModel scs WHERE scs.participant.id = :participantId AND scs.component.id IN :componentIds", StudentComponentScoreModel.class)
                .setParameter("participantId", participantId)
                .setParameter("componentIds", componentIds)
                .getResultList();
    }

    public List<StudentComponentScoreModel> findByParticipantAndComponentIds(List<Integer> participantId, Integer componentIds) {
        return em.createQuery("SELECT scs FROM StudentComponentScoreModel scs WHERE scs.participant.id IN :participantIds AND scs.component.id = :componentId", StudentComponentScoreModel.class)
                .setParameter("participantIds", participantId)
                .setParameter("componentId", componentIds)
                .getResultList();
    }

    // Getting all components for that participant as a list
//    public Map<Integer, List<StudentComponentScoreModel>> findByParticipantAndComponentIds(List<Integer> participantId, List<Integer> componentIds) {
//        List<StudentComponentScoreModel> results = em.createQuery(
//                "SELECT scs FROM StudentComponentScoreModel scs " +
//                "WHERE scs.participant.id IN :participantIds " +
//                "AND scs.component.id IN :componentIds", StudentComponentScoreModel.class)
//                .setParameter("participantIds", participantId)
//                .setParameter("componentIds", componentIds)
//                .getResultList();
//
//        return results.stream()
//                .collect(Collectors.groupingBy(scs -> scs.getParticipant().getId()));
//
//    }

    // Get a map of student adds as keys to the values which are Component ID and score of that component achieved by that student
    public Map<Integer, Map<String, Double>> findByParticipantAndComponentIds(List<Integer> participantId, List<Integer> componentIds) {
        List<StudentComponentScoreModel> results = em.createQuery(
                "SELECT scs FROM StudentComponentScoreModel scs " +
                "JOIN FETCH scs.participant " +
                "JOIN FETCH scs.component " +
                "WHERE scs.participant.id IN :participantIds " +
                "AND scs.component.id IN :componentIds", StudentComponentScoreModel.class)
                .setParameter("participantIds", participantId)
                .setParameter("componentIds", componentIds)
                .getResultList();

        return results.stream()
                .collect(Collectors.groupingBy(
                        scs -> scs.getParticipant().getStudentId(),
                        Collectors.toMap(
                                scs -> scs.getComponent().getName(),
                                StudentComponentScoreModel::getScore
                        )
                ));
    }

    public void update(StudentComponentScoreModel event) {
        em.merge(event);
    }

    public void deleteByParticipantAndComponentIds(int participantId, int componentId) {
        em.remove(findByParticipantAndComponentIds(participantId, componentId));
    }
}
