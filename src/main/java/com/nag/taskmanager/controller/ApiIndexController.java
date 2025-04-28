// Controller class to handle HTTP requests

package com.nag.taskmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class ApiIndexController {

    @GetMapping("")
    public String getApiIndex(Model model) {
        // Add endpoint information to the model
        model.addAttribute("endpoints", List.of(
                Map.of("name", "Persons", "url", "/api/persons"),
                Map.of("name", "Rooms", "url", "/api/rooms"),
                Map.of("name", "Tasks", "url", "/api/tasks")
        ));

        return "api-index";
    }
}
