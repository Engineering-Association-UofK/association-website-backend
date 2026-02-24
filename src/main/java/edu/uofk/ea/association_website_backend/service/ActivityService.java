package edu.uofk.ea.association_website_backend.service;

import edu.uofk.ea.association_website_backend.exceptionHandlers.exceptions.GenericNotFoundException;
import edu.uofk.ea.association_website_backend.model.activity.*;
import edu.uofk.ea.association_website_backend.repository.activity.ActivityEventRepo;
import edu.uofk.ea.association_website_backend.repository.activity.DailyActivityRepo;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

///
@Service
public class ActivityService {

    private final ActivityEventRepo activityEventRepo;
    private final DailyActivityRepo dailyActivityRepo;

    @Autowired
    public ActivityService(ActivityEventRepo activityEventRepo, DailyActivityRepo dailyActivityRepo) {
        this.activityEventRepo = activityEventRepo;
        this.dailyActivityRepo = dailyActivityRepo;
    }

    @Transactional
    public void log(ActivityType type, Map<String, Object> metaData, int adminId) {
        ActivityEventModel activity = new ActivityEventModel(
                type,
                adminId,
                metaData
        );
        activity.setCreatedAt(Instant.now());

        DailyActivityModel dailyActivity = new DailyActivityModel(
                adminId,
                LocalDate.now()
        );
        activityEventRepo.save(activity);
        dailyActivityRepo.save(dailyActivity);
    }

    ///  Personal

    public List<ActivityResponse> getActivitiesByDate(LocalDate date, int adminId) {
        var day = getStartEnd(date);
        List<ActivityEventModel> models = activityEventRepo.findByUserAndDate(adminId, day.a, day.b);
        if (models == null) return new ArrayList<>();
        return parseActivities(models);
    }

    public DailyActivityResponse getActivityCountByDate(LocalDate date, int adminId) {
        DailyActivityModel model = dailyActivityRepo.findByUserAndDate(adminId, date);
        if (model == null) return new DailyActivityResponse(date);
        return new DailyActivityResponse(
                model.getDate(),
                model.getActivityCount()
        );
    }

    public List<DailyActivityResponse> getLastYearActivityCounts(int adminId) {
        List<DailyActivityModel> models = dailyActivityRepo.findLastYearActivity(adminId);
        if (models == null) return new ArrayList<>();
        return parseDailyActivities(models);
    }

    /// Management

    public List<ActivityResponse> getAllActivities() {
        List<ActivityEventModel> models = activityEventRepo.getAll();
        if (models == null) return new ArrayList<>();
        return parseActivities(models);
    }

    public List<ActivityResponse> getActivitiesByType(ActivityType type) {
        List<ActivityEventModel> models = activityEventRepo.findByType(type);
        if (models == null) return new ArrayList<>();
        return parseActivities(models);
    }

    public List<ActivityResponse> getActivitiesByDate(LocalDate date) {
        var day = getStartEnd(date);
        List<ActivityEventModel> models = activityEventRepo.findByDate(day.a, day.b);
        if (models == null) return new ArrayList<>();
        return parseActivities(models);
    }

    public List<ActivityResponse> getActivityByDateAndType(LocalDate date, ActivityType type) {
        var day = getStartEnd(date);
        List<ActivityEventModel> models = activityEventRepo.findByTypeAndDate(type, day.a, day.b);
        if (models == null) return new ArrayList<>();
        return parseActivities(models);
    }

    /// Specific

    public ActivityEventModel getActivity(int id) {
        ActivityEventModel model = activityEventRepo.findById(id);
        if (model == null) throw new GenericNotFoundException("Activity not found");
        return model;
    }

    private List<DailyActivityResponse> parseDailyActivities(List<DailyActivityModel> models) {
        List<DailyActivityResponse> responses = new ArrayList<>();
        for (DailyActivityModel model : models) {
            DailyActivityResponse response = new DailyActivityResponse(
                    model.getDate(),
                    model.getActivityCount()
            );
            responses.add(response);
        }
        return responses;
    }

    private List<ActivityResponse> parseActivities(List<ActivityEventModel> models) {
        List<ActivityResponse> responses = new ArrayList<>();
        for (ActivityEventModel model : models) {
            ActivityResponse response = new ActivityResponse(
                    model.getEventType(),
                    model.getCreatedAt(),
                    model.getMetaData());
            responses.add(response);
        }
        return responses;
    }

    private Pair<Instant, Instant> getStartEnd(LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = start.plusDays(1);
        Instant startInstant = start.atZone(ZoneId.systemDefault()).toInstant();
        Instant endInstant = end.atZone(ZoneId.systemDefault()).toInstant();
        return new Pair<>(startInstant, endInstant);
    }
}
