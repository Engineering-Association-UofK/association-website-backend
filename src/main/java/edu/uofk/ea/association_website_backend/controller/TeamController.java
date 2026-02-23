package edu.uofk.ea.association_website_backend.controller;

import edu.uofk.ea.association_website_backend.model.TeamMemberModel;
import edu.uofk.ea.association_website_backend.model.activity.ActivityType;
import edu.uofk.ea.association_website_backend.service.ActivityService;
import edu.uofk.ea.association_website_backend.service.AdminDetailsService;
import edu.uofk.ea.association_website_backend.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/team")
public class TeamController {

    private final TeamService service;
    private final ActivityService activityService;
    private final AdminDetailsService adminDetailsService;

    @Autowired
    public TeamController(TeamService service, ActivityService activityService, AdminDetailsService adminDetailsService) {
        this.service = service;
        this.activityService = activityService;
        this.adminDetailsService = adminDetailsService;
    }

    @GetMapping
    public List<TeamMemberModel> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CONTENT_EDITOR', 'SUPER_ADMIN')")
    public TeamMemberModel getById(@PathVariable int id) {
        return service.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('CONTENT_EDITOR', 'SUPER_ADMIN')")
    public void save(@RequestBody TeamMemberModel request, Authentication authentication) {
        service.save(request);
        int id = adminDetailsService.getId(authentication.getName());
        activityService.log(ActivityType.CREATE_TEAM_MEMBER, Map.of("name", request.getName(), "position", request.getPosition()), id);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('CONTENT_EDITOR', 'SUPER_ADMIN')")
    public void update(@RequestBody TeamMemberModel request, Authentication authentication) {
        service.update(request);
        int id = adminDetailsService.getId(authentication.getName());
        activityService.log(ActivityType.UPDATE_TEAM_MEMBER, Map.of("id", request.getId(), "name", request.getName()), id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('CONTENT_EDITOR', 'SUPER_ADMIN')")
    public void delete(@PathVariable int id, Authentication authentication) {
        service.delete(id);
        int adminId = adminDetailsService.getId(authentication.getName());
        activityService.log(ActivityType.DELETE_TEAM_MEMBER, Map.of("id", id), adminId);
    }
}
