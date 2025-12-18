package com.integrate.core.controllers;

import com.integrate.core.dto.ProjectRequest;
import com.integrate.core.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/create")
    public ResponseEntity<?> createProject(@RequestBody ProjectRequest pRequest) {
        ProjectRequest cleaned = new ProjectRequest(
                pRequest.name().trim(),
                pRequest.description().trim(),
                pRequest.role().trim()
        );
        return projectService.createProject(cleaned);
    }
}
