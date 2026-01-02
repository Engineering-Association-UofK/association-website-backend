package edu.uofk.ea.association_website_backend.controller;

import edu.uofk.ea.association_website_backend.model.AdminModel;
import edu.uofk.ea.association_website_backend.service.AdminDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin")
public class AdminController {

    private final AdminDetailsService service;

    @Autowired
    public AdminController(AdminDetailsService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public String Login(@RequestBody AdminModel admin) {
        return service.Login(admin);
    }
}
