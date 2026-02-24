package edu.uofk.ea.association_website_backend.service;

import edu.uofk.ea.association_website_backend.exceptionHandlers.exceptions.GenericNotFoundException;
import edu.uofk.ea.association_website_backend.model.TeamMemberModel;
import edu.uofk.ea.association_website_backend.model.TeamMemberRequest;
import edu.uofk.ea.association_website_backend.repository.TeamRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    private TeamRepo repo;

    @Autowired
    public TeamService(TeamRepo repo) {
        this.repo = repo;
    }

    public List<TeamMemberModel> getAll() {
        return repo.getAll();
    }

    public TeamMemberModel findById(int id){
        if (repo.findById(id) == null) {
            throw new GenericNotFoundException("Team member not found with ID:" + id);
        }
        return repo.findById(id);
    }

    @Transactional
    public void save(TeamMemberRequest request){
        TeamMemberModel member = new TeamMemberModel(request.getName(), request.getPosition(), request.getDescription(), request.getImageLink());

        repo.save(member);
    }

    @Transactional
    public void update(TeamMemberRequest request) {
        if (repo.findById(request.getId()) == null) {
            throw new GenericNotFoundException("Team member not found with ID:" + request.getId());
        }
        TeamMemberModel model = new TeamMemberModel(request.getId(), request.getName(), request.getPosition(), request.getDescription(), request.getImageLink());

        repo.update(model);
    }

    @Transactional
    public void delete(int id) {
        if (repo.findById(id) == null) {
            throw new GenericNotFoundException("Team member not found with ID:" + id);
        }
        repo.delete(id);
    }
}
