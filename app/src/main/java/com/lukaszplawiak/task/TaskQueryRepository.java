package com.lukaszplawiak.task;

import com.lukaszplawiak.task.dto.TaskDto;

import java.util.Optional;
import java.util.Set;

public interface TaskQueryRepository {  // to jest repo do odczytu
    int count();

    Optional<TaskDto> findDtoById(int id);

    boolean existsByDoneIsFalseAndProject_Id(int id); // zaczynamy od exists to zwracamy True lub False

    <T> Set<T> findBy(Class<T> type); // tutaj trzeba uzyc SET inaczej nie zadziala

}
