package com.lukaszplawiak.task;

import java.util.List;
import java.util.Optional;

interface TaskRepository { // to jest repo do zapisu

    Optional<Task> findById(Integer id); // metoda ktora odzcytuje tylko pojedynczy task oraz tak która zapisuje pojedynczy task tp sa dwie rzeczy które zostawiamy w tym repo - to jeset repo do zapisu

    Task save(Task entity);

    List<Task> saveAll(Iterable<Task> entities);

    void deleteById(Integer id);
}
