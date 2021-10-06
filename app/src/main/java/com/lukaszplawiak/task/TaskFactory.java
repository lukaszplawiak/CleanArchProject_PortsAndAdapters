package com.lukaszplawiak.task;

import com.lukaszplawiak.project.dto.SimpleProjectQueryEntity;
import com.lukaszplawiak.task.dto.TaskDto;
import org.springframework.stereotype.Service;

@Service
public class TaskFactory {  // sposób na tworzenie taska(czyli encji) taka fabryka tworzy obiekt

    Task from(final TaskDto source, final SimpleProjectQueryEntity project) {
        var result = new Task(source.getDescription(), source.getDeadline() , project);
        result.setId(source.getId());
        result.setDone(source.isDone());
        result.setAdditionalComment(source.getAdditionalComment());
        return result;
    }
}
