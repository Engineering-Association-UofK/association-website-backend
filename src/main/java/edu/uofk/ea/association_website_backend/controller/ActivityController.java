package edu.uofk.ea.association_website_backend.controller;

import edu.uofk.ea.association_website_backend.model.activity.ActivityResponse;
import edu.uofk.ea.association_website_backend.model.activity.DailyActivityResponse;
import edu.uofk.ea.association_website_backend.service.ActivityService;
import edu.uofk.ea.association_website_backend.service.AdminDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/activity")
public class ActivityController {

    private final ActivityService activityService;
    private final AdminDetailsService adminDetailsService;

    @Autowired
    public ActivityController(ActivityService activityService, AdminDetailsService adminDetailsService) {
        this.activityService = activityService;
        this.adminDetailsService = adminDetailsService;
    }

    @GetMapping("/today")
    @PreAuthorize("hasAnyRole('TECHNICAL_SUPPORT', 'SUPER_ADMIN')")
    public List<ActivityResponse> getTodayActivities(Authentication authentication) {
        int adminId = adminDetailsService.getId(authentication.getName());
        return activityService.getTodayActivities(adminId);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('TECHNICAL_SUPPORT', 'SUPER_ADMIN')")
    public ActivityResponse getActivity(@PathVariable int id) {
        return activityService.getActivity(id);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('TECHNICAL_SUPPORT', 'SUPER_ADMIN')")
    public List<ActivityResponse> getAllActivities() {
        return activityService.getAllActivities();
    }

    @GetMapping("/daily-count")
    @PreAuthorize("hasAnyRole('TECHNICAL_SUPPORT', 'SUPER_ADMIN')")
    public DailyActivityResponse getDailyActivityCount(Authentication authentication) {
        int adminId = adminDetailsService.getId(authentication.getName());
        return activityService.getDailyActivityCount(adminId);
    }

    @GetMapping("/last-year-counts")
    @PreAuthorize("hasAnyRole('TECHNICAL_SUPPORT', 'SUPER_ADMIN')")
    public List<DailyActivityResponse> getLastYearActivityCounts(Authentication authentication) {
        int adminId = adminDetailsService.getId(authentication.getName());
        return activityService.getLastYearActivityCounts(adminId);
    }
}
