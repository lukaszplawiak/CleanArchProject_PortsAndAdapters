package com.lukaszplawiak.task;

import com.lukaszplawiak.DomainEventPublisher;
import com.lukaszplawiak.task.dto.TaskDto;
import com.lukaszplawiak.task.vo.TaskCreator;
import com.lukaszplawiak.task.vo.TaskEvent;

import java.util.List;
import java.util.Set;
import java.util.logging.ConsoleHandler;

import static java.util.stream.Collectors.toList;


public class TaskFacade {  // pełni rolę takiego trochekoordynatora tasków
    private final TaskFactory taskFactory;
    private final TaskRepository taskRepository;
    private final DomainEventPublisher publisher;

    TaskFacade(final TaskFactory taskFactory, final TaskRepository taskRepository, final DomainEventPublisher publisher) {
        this.taskFactory = taskFactory;
        this.taskRepository = taskRepository;
        this.publisher = publisher;
    }

    public List<TaskDto> createTasks(final Set<TaskCreator> tasks) {
        return taskRepository.saveAll(
                        tasks.stream().map(Task::createFrom)
                                .collect(toList())
                ).stream().map(this::toDto)
                .collect(toList());
    }


    TaskDto save(TaskDto toSave) {
        return toDto(taskRepository.save(
                taskRepository.findById(toSave.getId())
                        .map(existingTask -> {
                            if (existingTask.getSnapshot().isDone() != toSave.isDone()) {
                                publisher.publisher(existingTask.toggle());
                            }
                            publisher.publisher(existingTask.updateInfo(
                                    toSave.getDescription(),
                                    toSave.getDeadline(),
                                    toSave.getAdditionalComment()
                            ));
                            return existingTask;
                        }).orElseGet(() -> taskFactory.from(toSave))
        ));
    }

    void delete(int id) {
        taskRepository.findById(id)
                        .ifPresent(task -> {
                            taskRepository.deleteById(id);
                            publisher.publisher(new TaskEvent(task.getSnapshot().getSourceId(),
                                    TaskEvent.State.DELETED,
                                    null
                                    ));
                        });

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
