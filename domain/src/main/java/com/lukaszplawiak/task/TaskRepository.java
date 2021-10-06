package com.lukaszplawiak.task;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

interface TaskRepository extends Repository<Task, Integer> { // to jest repo do zapisu

    Optional<Task> findById(Integer id); // metoda ktora odzcytuje tylko pojedynczy task oraz tak która zapisuje pojedynczy task tp sa dwie rzeczy które zostawiamy w tym repo - to jeset repo do zapisu

    <S extends Task> S save(S entity);

    <S extends Task> List<S> saveAll(Iterable<S> entities);

    void deleteById(Integer id);
}
