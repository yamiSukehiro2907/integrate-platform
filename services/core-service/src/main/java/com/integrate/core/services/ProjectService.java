package com.integrate.core.services;

import com.integrate.core.config.UserContext;
import com.integrate.core.domain.Project;
import com.integrate.core.dto.ApiResponse;
import com.integrate.core.dto.ProjectRequest;
import com.integrate.core.helpers.ProjectHelper;
import com.integrate.core.repositories.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ResponseEntity<?> createProject(ProjectRequest projectRequest) {
        Project project = new Project();
        project.setName(projectRequest.name());
        project.setDescription(projectRequest.description());
        project.setOwnerId(UserContext.getUserId());
        project.setMembers(new ArrayList<>());
        project.addMember(UserContext.getUserId(), (projectRequest.role().isEmpty()) ? "OWNER" : projectRequest.role());

        Project savedProject = projectRepository.save(project);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Project created Successfully!", ProjectHelper.createProjectDto(savedProject)));
    }
}
