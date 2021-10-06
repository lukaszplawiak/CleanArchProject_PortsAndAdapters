package com.lukaszplawiak.project;


import com.lukaszplawiak.project.dto.ProjectDto;

import java.util.stream.Collectors;

class ProjectFactory {
    Project from(ProjectDto source) {
        var result = new Project();
        result.setId(source.getId());
        result.setName(source.getName());
        source.getSteps().forEach(stepSource -> {
            var step = new ProjectStep(stepSource.getDescription(), stepSource.getDaysToProjectDeadline(), result);
            step.setId(stepSource.getId());
            result.addStep(step);
        });
        return result;
    }
}
