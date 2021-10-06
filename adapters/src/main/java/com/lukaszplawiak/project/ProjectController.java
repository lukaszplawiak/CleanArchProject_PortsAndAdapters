package com.lukaszplawiak.project;

import com.lukaszplawiak.project.dto.ProjectDeadlineDto;
import com.lukaszplawiak.project.dto.ProjectDto;
import com.lukaszplawiak.task.dto.TaskDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/projects")
class ProjectController {
    private final ProjectFacade projectFacade;
    private final ProjectQueryRepository projectQueryRepository;

    ProjectController(final ProjectFacade projectFacade, final ProjectQueryRepository projectQueryRepository) {
        this.projectFacade = projectFacade;
        this.projectQueryRepository = projectQueryRepository;
    }

    @GetMapping
    List<ProjectDto> list() {
        return projectQueryRepository.findBy();
    }

    @GetMapping("/{id}")
    ResponseEntity<ProjectDto> get(@PathVariable int id) {
        return projectQueryRepository.findDtoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    ResponseEntity<Project> update(@PathVariable int id, @RequestBody ProjectDto toUpdate) {
        if (id != toUpdate.getId() && toUpdate.getId() != 0) {
            throw new IllegalStateException("Id in URL is different than in body: " + id + " and " + (toUpdate.getId() == 0 ? "empty" : toUpdate.getId()));
        }
        projectFacade.save(toUpdate);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    ResponseEntity<ProjectDto> create(@RequestBody ProjectDto toCreate) {
        ProjectDto result = projectFacade.save(toCreate);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @PostMapping("/{id}/tasks")
    List<TaskDto> createTasks(@PathVariable int id, @RequestBody ProjectDeadlineDto deadlineDto) {
        return projectFacade.createTasks(id, deadlineDto.getDeadline());
    }

    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<String> handleClientError(IllegalStateException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
