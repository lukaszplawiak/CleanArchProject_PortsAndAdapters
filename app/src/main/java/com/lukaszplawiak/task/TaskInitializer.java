package com.lukaszplawiak.task;

import java.time.ZonedDateTime;

public class TaskInitializer {
    private final TaskRepository taskRepository;
    private final TaskQueryRepository taskQueryRepository;

    public TaskInitializer(final TaskRepository taskRepository, final TaskQueryRepository taskQueryRepository) {
        this.taskRepository = taskRepository;
        this.taskQueryRepository = taskQueryRepository;
    }

    void init() {
        if (taskQueryRepository.count() == 0) {
            taskRepository.save(Task.restore(
                    new TaskSnapshot(
                            0,
                            "Example Iddd",
                            false,
                            ZonedDateTime.now(),
                            0,
                            null,
                            null
                    )
            ));
        }
    }
}
