package edu.uofk.ea.association_website_backend.service;

import edu.uofk.ea.association_website_backend.exceptionHandlers.exceptions.GenericNotFoundException;
import edu.uofk.ea.association_website_backend.model.Language;
import edu.uofk.ea.association_website_backend.model.bot.BotCommandDTO;
import edu.uofk.ea.association_website_backend.model.bot.BotResponse;
import edu.uofk.ea.association_website_backend.model.bot.CommandRequest;
import edu.uofk.ea.association_website_backend.model.bot.CommandModel;
import edu.uofk.ea.association_website_backend.model.bot.CommandOption;
import edu.uofk.ea.association_website_backend.repository.BotCommandRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BotService {

    private final BotCommandRepo repo;

    @Autowired
    public BotService(BotCommandRepo repo) {
        this.repo = repo;
    }

    public BotResponse getResponse(CommandRequest request) {
        String keyword = request.getKeyword();
        if (keyword == null || keyword.isBlank()) {
            keyword = "@root";
        }

        Language lang = request.getLang() == null ? Language.en : request.getLang();

        CommandModel command = repo.findByKeyword(keyword);
        if (command == null) {
            return new BotResponse("Sorry, I don't understand that command.", true, Collections.emptyList());
        }

        String responseText = command.getText(lang);
        boolean isFinal = command.getNextKeywords() == null || command.getNextKeywords().isEmpty();

        if (isFinal) {
            return new BotResponse(responseText, true, Collections.emptyList());
        } else {
            List<CommandModel> nextCommands = repo.findAllByKeywordIn(command.getNextKeywords());
            List<CommandOption> options = nextCommands.stream()
                    .map(c -> new CommandOption(c.getTrigger(lang), c.getKeyword()))
                    .collect(Collectors.toList());
            return new BotResponse(responseText, false, options);
        }
    }

    public void save(BotCommandDTO dto) {
        CommandModel model = new CommandModel();
        model.setKeyword(dto.getKeyword());
        model.setTriggers(dto.getTriggers());
        model.setDescription(dto.getDescription());
        model.setTexts(dto.getTexts());
        model.setNextKeywords(dto.getNextKeywords());
        repo.save(model);
    }

    public void update(BotCommandDTO dto) {
        if (dto.getId() == null) throw new IllegalArgumentException("ID is required for update");
        
        CommandModel model = repo.findById(dto.getId());
        if (model == null) throw new GenericNotFoundException("Command not found");

        model.setKeyword(dto.getKeyword());
        model.setTriggers(dto.getTriggers());
        model.setDescription(dto.getDescription());
        
        // Update collections
        model.setTexts(dto.getTexts());
        model.setNextKeywords(dto.getNextKeywords());
        
        repo.update(model);
    }

    public void delete(int id) {
        if (repo.findById(id) == null) throw new GenericNotFoundException("Command not found");
        repo.delete(id);
    }
}
