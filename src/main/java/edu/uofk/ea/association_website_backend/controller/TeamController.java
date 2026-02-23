package edu.uofk.ea.association_website_backend.controller;

import edu.uofk.ea.association_website_backend.model.TeamMemberModel;
import edu.uofk.ea.association_website_backend.model.TeamMemberRequest;
import edu.uofk.ea.association_website_backend.service.TeamService;
import jakarta.validation.Valid;
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
    @PreAuthorize("hasAnyRole('CONTENT_EDITOR', 'SUPER_ADMIN')")
    public TeamMemberModel getById(@PathVariable int id) {
        return service.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('CONTENT_EDITOR', 'SUPER_ADMIN')")
    public void save(@Valid @RequestBody TeamMemberRequest request) {
        service.save(request);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('CONTENT_EDITOR', 'SUPER_ADMIN')")
    public void update(@Valid @RequestBody TeamMemberRequest request) {
        service.update(request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('CONTENT_EDITOR', 'SUPER_ADMIN')")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}
