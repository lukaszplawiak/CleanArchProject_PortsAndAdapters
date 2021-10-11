package com.lukaszplawiak.task;

import com.lukaszplawiak.project.dto.SimpleProject;
import com.lukaszplawiak.task.dto.TaskDto;


public class TaskFactory {  // sposób na tworzenie taska(czyli encji) taka fabryka tworzy obiekt

    Task from(final TaskDto source, final SimpleProject project) {
        return Task.restore(
                new TaskSnapshot(
                        source.getId(),
                        source.getDescription(),
                        source.isDone(),
                        source.getDeadline(),
                        0,
                        source.getAdditionalComment(),
                        project.getSnapshot()
                )
        );
    }
}
