package edu.uofk.ea.association_website_backend.repository.activity;

import edu.uofk.ea.association_website_backend.model.activity.DailyActivityModel;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DailyActivityRepo {

    private final EntityManager em;

    @Autowired
    public DailyActivityRepo(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public void save(DailyActivityModel model) {
        em.createNativeQuery("""
            INSERT INTO daily_activity (admin_id, date, activity_count)
            VALUES (:adminId, :date, 1)
            ON DUPLICATE KEY UPDATE activity_count = activity_count + 1
            """)
                .setParameter("adminId", model.getAdminId())
                .setParameter("date", model.getDate())
                .executeUpdate();
    }

    public DailyActivityModel findByUserAndDate(int UserId, LocalDate date) {
        return em.createQuery("SELECT a FROM DailyActivityModel a WHERE a.adminId = :adminId AND a.date = :date", DailyActivityModel.class)
                .setParameter("adminId", UserId)
                .setParameter("date", date)
                .getResultList().stream().findFirst().orElse(null);
    }

    // Get last year activity, sorted from older first to newer last
    public List<DailyActivityModel> findLastYearActivity(int userId) {
        LocalDate lastYear = LocalDate.now().minusYears(1);
        return em.createQuery("SELECT a FROM DailyActivityModel a WHERE a.adminId = :adminId AND a.date >= :lastYear ORDER BY a.date ASC", DailyActivityModel.class)
                .setParameter("adminId", userId)
                .setParameter("lastYear", lastYear)
                .getResultList();
    }

    // Get from a specific date to now, sorted from older first to newer last
    public List<DailyActivityModel> findFromDate(int userId, LocalDate date) {
        return em.createQuery("SELECT a FROM DailyActivityModel a WHERE a.adminId = :adminId AND a.date >= :date ORDER BY a.date ASC", DailyActivityModel.class)
                .setParameter("adminId", userId)
                .setParameter("date", date)
                .getResultList();
    }

    // Get from a specific date to another date, sorted from older first to newer last
    public List<DailyActivityModel> findBetweenDates(int userId, LocalDate startDate, LocalDate endDate) {
        return em.createQuery("SELECT a FROM DailyActivityModel a WHERE a.adminId = :adminId AND a.date BETWEEN :startDate AND :endDate ORDER BY a.date ASC", DailyActivityModel.class)
                .setParameter("adminId", userId)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }
}
