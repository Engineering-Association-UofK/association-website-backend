package edu.uofk.ea.association_website_backend.controller;

import edu.uofk.ea.association_website_backend.model.bot.BotCommandDTO;
import edu.uofk.ea.association_website_backend.model.bot.BotResponse;
import edu.uofk.ea.association_website_backend.model.bot.CommandRequest;
import edu.uofk.ea.association_website_backend.service.BotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bot")
@Tag(
        name = "Chatbot",
        description = "Endpoints for interacting with and managing the automated chatbot system"
)
public class BotController {

    private final BotService botService;

    @Autowired
    public BotController(BotService botService) {
        this.botService = botService;
    }

    @PostMapping("/command")
    @Operation(
            summary = "Get chatbot response",
            description = "Processes a user command or keyword and returns the corresponding bot response and options."
    )
    public BotResponse getBotResponse(@Valid @RequestBody CommandRequest request) {
        return botService.getResponse(request);
    }

    @GetMapping("/manage")
    @PreAuthorize("hasAnyRole('CONTENT_EDITOR', 'SUPER_ADMIN')")
    @Operation(
            summary = "Get all bot commands",
            description = "Retrieves a list of all configured bot commands for management purposes."
    )
    public List<BotCommandDTO> getAllCommands() {
        return botService.getAll();
    }

    @GetMapping("/manage/{id}")
    @PreAuthorize("hasAnyRole('CONTENT_EDITOR', 'SUPER_ADMIN')")
    @Operation(
            summary = "Get a bot command by ID",
            description = "Retrieves the details of a specific bot command using its unique identifier for management purposes."
    )
    public BotCommandDTO getCommand(@PathVariable int id) {
        return botService.getById(id);
    }

    @PostMapping("/manage")
    @PreAuthorize("hasAnyRole('CONTENT_EDITOR', 'SUPER_ADMIN')")
    @Operation(
            summary = "Create a new bot command",
            description = "Adds a new command to the chatbot system, including its keywords, triggers, and localized responses."
    )
    public void addCommand(@Valid @RequestBody BotCommandDTO request) {
        botService.save(request);
    }

    @PutMapping("/manage")
    @PreAuthorize("hasAnyRole('CONTENT_EDITOR', 'SUPER_ADMIN')")
    @Operation(
            summary = "Update an existing bot command",
            description = "Updates an existing command's details, including its keywords, triggers, and localized responses."
    )
    public void updateCommand(@Valid @RequestBody BotCommandDTO request) {
        botService.update(request);
    }

    @DeleteMapping("/manage/{id}")
    @PreAuthorize("hasAnyRole('CONTENT_EDITOR', 'SUPER_ADMIN')")
    @Operation(
            summary = "Delete a bot command",
            description = "Removes a specific command from the chatbot system using its unique identifier."
    )
    public void deleteCommand(@PathVariable int id) {
        botService.delete(id);
    }
}