package edu.uofk.ea.association_website_backend.controller;

import edu.uofk.ea.association_website_backend.model.TeamMemberModel;
import edu.uofk.ea.association_website_backend.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/team")
public class TeamController {

    private final TeamService service;

    @Autowired
    public TeamController(TeamService service) {
        this.service = service;
    }

    @GetMapping
    public List<TeamMemberModel> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public TeamMemberModel getById(@PathVariable int id) {
        return service.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public void save(@RequestBody TeamMemberModel request) {
        service.save(request);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public void update(@RequestBody TeamMemberModel request) {
        service.update(request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}
