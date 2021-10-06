package com.lukaszplawiak.task;

import com.lukaszplawiak.task.dto.TaskDto;
import com.lukaszplawiak.task.dto.TaskWithChangesDto;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TaskQueryRepository extends Repository<Task, Integer> {  // to jest repo do odczytu
    int count();

    boolean existsByDoneIsFalseAndProject_Id(int id); // zaczynamy od exists to zwracamy True lub False

    Optional<TaskDto> findDtoById(int id);

    List<TaskDto> findAllBy();

    List<TaskWithChangesDto> findAllWithChangesBy(); // dobrze w 'tych' metodach dawac na koncy 'By' po to aby spring polapal sie o co chodzi, bo spring kiedy widzi 'By' a za nim nic wiecej nie ma to mysli ze to jest metoda, inaczej pomyslalby ze to jest pole; dzieki temy 'By' spring wie ze to jest projekcja

    <T> Set<T> findBy(Class<T> type); // tutaj trzeba uzyc SET inaczej nie zadziala

}
