package com.lukaszplawiak.project;

import com.lukaszplawiak.project.dto.ProjectDto;

import java.util.stream.Collectors;


class ProjectFactory {
    Project from(ProjectDto source) {
        return Project.restore(new ProjectSnapshot(
                source.getId(),
                source.getName(),
                source.getSteps().stream()
                        .map(stepDto -> new ProjectStepSnapshot(
                                        stepDto.getId(),
                                        stepDto.getDescription(),
                                        stepDto.getDaysToProjectDeadline()
                                )
                        ).collect(Collectors.toSet())
        ));
    }
}
