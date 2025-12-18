package com.integrate.core.helpers;

import com.integrate.core.domain.Project;
import com.integrate.core.domain.ProjectMember;
import com.integrate.core.dto.MemberDto;
import com.integrate.core.dto.ProjectDto;

import java.util.List;

public class ProjectHelper {

    public static ProjectDto createProjectDto(Project project) {
        List<ProjectMember> projectMemberList = project.getMembers();
        List<MemberDto> memberDtoList = projectMemberList.stream()
                .map((projectMember) ->
                        new MemberDto(projectMember.getUserId(), projectMember.getProjectRole())
                ).toList();
        return new ProjectDto(
                project.getId(),
                project.getName(),
                project.getDescription(),
                memberDtoList,
                project.getCreatedAt(),
                project.getUpdatedAt()
        );
    }
}
