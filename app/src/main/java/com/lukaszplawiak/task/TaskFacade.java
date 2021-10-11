package com.lukaszplawiak.task;

import com.lukaszplawiak.project.dto.SimpleProject;
import com.lukaszplawiak.task.dto.TaskDto;

import java.util.List;

import static java.util.stream.Collectors.toList;


public class TaskFacade {  // pełni rolę takiego trochekoordynatora tasków
    private final TaskFactory taskFactory;
    private final TaskRepository taskRepository;

    TaskFacade(final TaskFactory taskFactory, final TaskRepository taskRepository) {
        this.taskFactory = taskFactory;
        this.taskRepository = taskRepository;
    }

    public List<TaskDto> saveAll(final List<TaskDto> tasks, final SimpleProject project) {
        return taskRepository.saveAll(
                tasks.stream()
                        .map(dto -> taskFactory.from(dto, project))
                        .collect(toList())
        ).stream().map(this::toDto)
                .collect(toList());
    }

    TaskDto save(TaskDto toSave) {
        return toDto(taskRepository.save(
                taskRepository.findById(toSave.getId())
                        .map(existingTask -> {
                            if (existingTask.getSnapshot().isDone() != toSave.isDone()) {
                                existingTask.toggle();
                            }
                            existingTask.updateInfo(
                                    toSave.getDescription(),
                                    toSave.getDeadline(),
                                    toSave.getAdditionalComment()
                            );
                            return existingTask;
                        }).orElseGet(() -> taskFactory.from(toSave, null))
        ));
    }

    void delete(int id) {
        taskRepository.deleteById(id);
    }

    private TaskDto toDto(Task task) {
        TaskSnapshot snap = task.getSnapshot();
        return TaskDto.builder()
                .withId(snap.getId())
                .withDescription(snap.getDescription())
                .withDone(snap.isDone())
                .withDeadline(snap.getDeadline())
                .withAdditionalComment(snap.getAdditionalComment())
                .build();
    }

}
