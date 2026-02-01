package edu.uofk.ea.association_website_backend.controller;

import edu.uofk.ea.association_website_backend.model.bot.BotCommandDTO;
import edu.uofk.ea.association_website_backend.model.bot.BotResponse;
import edu.uofk.ea.association_website_backend.model.bot.CommandRequest;
import edu.uofk.ea.association_website_backend.service.BotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bot")
public class BotController {

    private final BotService botService;

    @Autowired
    public BotController(BotService botService) {
        this.botService = botService;
    }

    @PostMapping("/command")
    public BotResponse getBotResponse(@RequestBody CommandRequest request) {
        return botService.getResponse(request);
    }

    @PostMapping("/manage")
    @PreAuthorize("hasAnyRole('ADMIN_MANAGER', 'SUPER_ADMIN')")
    public void addCommand(@RequestBody BotCommandDTO request) {
        botService.save(request);
    }

    @PutMapping("/manage")
    @PreAuthorize("hasAnyRole('ADMIN_MANAGER', 'SUPER_ADMIN')")
    public void updateCommand(@RequestBody BotCommandDTO request) {
        botService.update(request);
    }

    @DeleteMapping("/manage/{id}")
    @PreAuthorize("hasAnyRole('ADMIN_MANAGER', 'SUPER_ADMIN')")
    public void deleteCommand(@PathVariable int id) {
        botService.delete(id);
    }
}