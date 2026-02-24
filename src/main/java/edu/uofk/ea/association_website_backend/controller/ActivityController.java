package edu.uofk.ea.association_website_backend.controller;

import edu.uofk.ea.association_website_backend.model.activity.ActivityEventModel;
import edu.uofk.ea.association_website_backend.model.activity.ActivityResponse;
import edu.uofk.ea.association_website_backend.model.activity.ActivityType;
import edu.uofk.ea.association_website_backend.model.activity.DailyActivityResponse;
import edu.uofk.ea.association_website_backend.service.ActivityService;
import edu.uofk.ea.association_website_backend.service.AdminDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/activity")
@Tag(
        name = "Activity Management",
        description = "Endpoints for tracking and viewing admin activities and logs"
)
public class ActivityController {

    private final ActivityService activityService;
    private final AdminDetailsService adminDetailsService;

    @Autowired
    public ActivityController(ActivityService activityService, AdminDetailsService adminDetailsService) {
        this.activityService = activityService;
        this.adminDetailsService = adminDetailsService;
    }

    ///  Personal

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Get personal activities",
            description = "Retrieves a list of activities performed by the authenticated admin on a specific date. Defaults to today if no date is provided."
    )
    public List<ActivityResponse> getActivities(@RequestParam(required = false) LocalDate date, Authentication authentication) {
        LocalDate effectiveDate = (date != null) ? date : LocalDate.now();
        int adminId = adminDetailsService.getId(authentication.getName());
        return activityService.getActivitiesByDate(effectiveDate, adminId);
    }

    @GetMapping("/count")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Get personal activity count",
            description = "Retrieves the count of activities performed by the authenticated admin on a specific date. Defaults to today if no date is provided."
    )
    public DailyActivityResponse getTodayActivityCount(@RequestParam(required = false) LocalDate date, Authentication authentication) {
        LocalDate effectiveDate = (date != null) ? date : LocalDate.now();
        int adminId = adminDetailsService.getId(authentication.getName());
        return activityService.getActivityCountByDate(effectiveDate, adminId);
    }

    @GetMapping("/last-year-counts")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Get last year activity counts",
            description = "Retrieves the count of activities performed by the authenticated admin in the last year."
    )
    public List<DailyActivityResponse> getLastYearActivityCounts(Authentication authentication) {
        int adminId = adminDetailsService.getId(authentication.getName());
        return activityService.getLastYearActivityCounts(adminId);
    }

    /// Management

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('TECHNICAL_SUPPORT', 'SUPER_ADMIN')")
    @Operation(
            summary = "Get all activities",
            description = "Retrieves a list of all activities. Can be filtered by date and activity type. Access restricted to Technical Support and Super Admins."
    )
    public List<ActivityResponse> getAllActivities(@RequestParam(required = false) LocalDate date, @RequestParam(required = false) ActivityType type) {
        if (date != null) {
            if (type != null) {
                return activityService.getActivityByDateAndType(date, type);
            }
            return activityService.getActivitiesByDate(date);
        } else {
            if (type != null) {
                return activityService.getActivitiesByType(type);
            }
            return activityService.getAllActivities();
        }
    }

    /// Specific

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('TECHNICAL_SUPPORT', 'SUPER_ADMIN')")
    @Operation(
            summary = "Get activity",
            description = "Retrieves a specific activity by its ID. Access restricted to Technical Support and Super Admins."
    )
    public ActivityEventModel getActivity(@PathVariable int id) {
        return activityService.getActivity(id);
    }
}
