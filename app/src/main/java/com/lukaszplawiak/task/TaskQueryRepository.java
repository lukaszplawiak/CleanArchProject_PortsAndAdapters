package com.lukaszplawiak.task;

import com.lukaszplawiak.task.dto.TaskDto;

import java.util.Optional;
import java.util.Set;

public interface TaskQueryRepository {
    int count();

    Optional<TaskDto> findDtoById(Integer id);

    <T> Set<T> findBy(Class<T> type);
}