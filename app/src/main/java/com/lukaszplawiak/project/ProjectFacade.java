package com.lukaszplawiak.project;

import com.lukaszplawiak.project.dto.ProjectDto;
import com.lukaszplawiak.project.dto.ProjectStepDto;
import com.lukaszplawiak.task.TaskFacade;
import com.lukaszplawiak.task.TaskQueryRepository;
import com.lukaszplawiak.task.dto.TaskDto;
import com.lukaszplawiak.task.vo.TaskCreator;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;


public class ProjectFacade {
    private final ProjectFactory projectFactory;
    private final ProjectRepository projectRepository;
    private final TaskFacade taskFacade;
    private final TaskQueryRepository taskQueryRepository;

    ProjectFacade(final ProjectFactory projectFactory, final ProjectRepository projectRepository, final TaskFacade taskFacade, final TaskQueryRepository taskQueryRepository) {
        this.projectFactory = projectFactory;
        this.projectRepository = projectRepository;
        this.taskFacade = taskFacade;
        this.taskQueryRepository = taskQueryRepository;
    }

    public ProjectDto save(ProjectDto dtoToSave) {
        if (dtoToSave.getId() != 0) {
            return toDto(saveWithId(dtoToSave));
        }
        if (dtoToSave.getSteps().stream().anyMatch(step -> step.getId() != 0)) {
            throw new IllegalStateException("Cannot add project with existing steps");
        }
        return toDto(projectRepository.save(projectFactory.from(dtoToSave)));
    }

    private Project saveWithId(ProjectDto dtoToSave) {
        return projectRepository.findById(dtoToSave.getId()).map(existingProject -> {
            Project toSave = projectFactory.from(dtoToSave);
            Set<Project.Step> removedSteps = existingProject.modifySteps(toSave.getSnapshot().getSteps());
            projectRepository.save(existingProject);
            removedSteps.forEach(projectRepository::delete);
            return existingProject;
        }).orElseGet(() -> projectRepository.save(projectFactory.from(dtoToSave)));
    }

    List<TaskDto> createTasks(int projectId, ZonedDateTime projectDeadline) {
        if (taskQueryRepository.existsByDoneIsFalseAndProject_Id(projectId)) {
            throw new IllegalStateException("There are still some undone tasks from a previous project instance!");
        }
        return projectRepository.findById(projectId).map(project -> {
            Set<TaskCreator> tasks = project.convertToTasks(projectDeadline);
            return taskFacade.createTasks(tasks);
        }).orElseThrow(() -> new IllegalArgumentException("No project found with id: " + projectId));
    }

    private ProjectDto toDto(Project project) {
        var snap = project.getSnapshot();
        return ProjectDto.create(snap.getId(), snap.getName(), snap.getSteps().stream().map(this::toDto).collect(toList()));
    }

    private ProjectStepDto toDto(ProjectStepSnapshot step) {
        return ProjectStepDto.create(step.getId(), step.getDescription(), step.getDaysToProjectDeadline());
    }
}
