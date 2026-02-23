package edu.uofk.ea.association_website_backend.service;

import edu.uofk.ea.association_website_backend.model.activity.*;
import edu.uofk.ea.association_website_backend.repository.activity.ActivityEventRepo;
import edu.uofk.ea.association_website_backend.repository.activity.DailyActivityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public List<ActivityResponse> getTodayActivities(int adminId) {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = start.plusDays(1);
        List<ActivityEventModel> models = activityEventRepo.findByUserAndDate(adminId, start, end);
        List<ActivityResponse> responses = new ArrayList<>();
        for (ActivityEventModel model : models) {
            ActivityResponse response = new ActivityResponse(
                    model.getEventType(),
                    model.getCreatedAt(),
                    model.getMetaData()
            );
            responses.add(response);
        }
        return responses;
    }

    public ActivityResponse getActivity(int id) {
        ActivityEventModel model = activityEventRepo.findById(id);
        return new ActivityResponse(
                model.getEventType(),
                model.getCreatedAt(),
                model.getMetaData()
        );
    }

    public List<ActivityResponse> getAllActivities() {
        List<ActivityEventModel> models = activityEventRepo.getAll();
        List<ActivityResponse> responses = new ArrayList<>();
        for (ActivityEventModel model : models) {
            ActivityResponse response = new ActivityResponse(
                    model.getEventType(),
                    model.getCreatedAt(),
                    model.getMetaData()
            );
            responses.add(response);
        }
        return responses;
    }

    public DailyActivityResponse getDailyActivityCount(int adminId) {
        DailyActivityModel model = dailyActivityRepo.findByUserAndDate(adminId, LocalDate.now());
        return new DailyActivityResponse(
                model.getDate(),
                model.getActivityCount()
        );
    }

    public List<DailyActivityResponse> getLastYearActivityCounts(int adminId) {
        List<DailyActivityModel> models = dailyActivityRepo.findLastYearActivity(adminId);
        List<DailyActivityResponse> response = new ArrayList<>();
        for (DailyActivityModel m : models) {
            DailyActivityResponse r = new DailyActivityResponse(
                    m.getDate(),
                    m.getActivityCount()
            );
            response.add(r);
        }
        return response;
    }
}
