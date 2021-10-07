package com.lukaszplawiak.project;

import com.lukaszplawiak.project.dto.ProjectDto;
import com.lukaszplawiak.project.dto.ProjectStepDto;
import com.lukaszplawiak.project.dto.SimpleProjectQueryEntity;
import com.lukaszplawiak.task.TaskFacade;
import com.lukaszplawiak.task.TaskQueryRepository;
import com.lukaszplawiak.task.dto.TaskDto;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;


public class ProjectFacade {
    private final ProjectFactory projectFactory;
    private final ProjectRepository projectRepository;
    private final ProjectStepRepository projectStepRepository;
    private final TaskFacade taskFacade;
    private final TaskQueryRepository taskQueryRepository;

    ProjectFacade(final ProjectFactory projectFactory, final ProjectRepository projectRepository, final ProjectStepRepository projectStepRepository, final TaskFacade taskFacade, final TaskQueryRepository taskQueryRepository) {
        this.projectFactory = projectFactory;
        this.projectRepository = projectRepository;
        this.projectStepRepository = projectStepRepository;
        this.taskFacade = taskFacade;
        this.taskQueryRepository = taskQueryRepository;
    }

    ProjectDto save(ProjectDto dtoToSave) {
        var toSave = projectFactory.from(dtoToSave);
        if (toSave.getId() != 0) {
            return toDto(saveWithId(toSave));
        }
        if (dtoToSave.getSteps().stream().anyMatch(step -> step.getId() != 0)) {
            throw new IllegalStateException("Cannot add project with existing steps");
        }
        return toDto(projectRepository.save(toSave));
    }

    private Project saveWithId(Project toSave) {
        return projectRepository.findById(toSave.getId()).map(existingProject -> {
            Set<ProjectStep> stepsToRemove = new HashSet<>();
            existingProject.setName(toSave.getName());
            existingProject.getSteps()
                    .forEach(existingStep -> toSave.getSteps().stream()
                            .filter(potentialOverride -> existingStep.getId() == potentialOverride.getId())
                            .findFirst()
                            .ifPresentOrElse(
                                    overridingStep -> {
                                        existingStep.setDescription(overridingStep.getDescription());
                                        existingStep.setDaysToProjectDeadline(overridingStep.getDaysToProjectDeadline());
                                    },
                                    () -> stepsToRemove.add(existingStep)
                            )
                    );
            stepsToRemove.forEach(toRemove -> {
                existingProject.removeStep(toRemove);
                projectStepRepository.delete(toRemove);
            });
            toSave.getSteps().stream()
                    .filter(newStep -> existingProject.getSteps().stream()
                            .noneMatch(existingStep -> existingStep.getId() == newStep.getId())
                    ).collect(toSet())
                    // collecting first to allow multiple id=0
                    .forEach(existingProject::addStep);
            projectRepository.save(existingProject);
            return existingProject;
        }).orElseGet(() -> {
            toSave.getSteps().forEach(step -> {
                if (step.getProject() == null) {
                    step.setProject(toSave);
                }
            });
            return projectRepository.save(toSave);
        });
    }

    List<TaskDto> createTasks(int projectId, ZonedDateTime projectDeadline) {
        if (taskQueryRepository.existsByDoneIsFalseAndProject_Id(projectId)) {
            throw new IllegalStateException("There are still some undone tasks from a previous project instance!");
        }
        return projectRepository.findById(projectId).map(project -> {
            List<TaskDto> tasks = project.getSteps().stream()
                    .map(step -> TaskDto.builder()
                            .withDescription(step.getDescription())
                            .withDeadline(projectDeadline.plusDays(step.getDaysToProjectDeadline()))
                            .build()
                    ).collect(toList());
            return taskFacade.saveAll(tasks, new SimpleProjectQueryEntity(projectId, project.getName()));
        }).orElseThrow(() -> new IllegalArgumentException("No project found with id: " + projectId));
    }

    private ProjectDto toDto(Project project) {
        return ProjectDto.create(project.getId(), project.getName(), project.getSteps().stream().map(this::toDto).collect(toSet()));
    }

    private ProjectStepDto toDto(ProjectStep step) {
        return ProjectStepDto.create(step.getId(), step.getDescription(), step.getDaysToProjectDeadline());
    }
}
