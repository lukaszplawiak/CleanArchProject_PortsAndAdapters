package com.lukaszplawiak.task;

import com.lukaszplawiak.task.dto.TaskDto;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

interface SqlTaskRepository extends Repository<TaskSnapshot, Integer> {
    Optional<TaskSnapshot> findById(Integer id); // metoda ktora odzcytuje tylko pojedynczy task oraz tak która zapisuje pojedynczy task tp sa dwie rzeczy które zostawiamy w tym repo - to jeset repo do zapisu

    TaskSnapshot save(TaskSnapshot entity);

    List<TaskSnapshot> saveAll(Iterable<TaskSnapshot> entities);

    void deleteById(Integer id);
}
@org.springframework.stereotype.Repository
class TaskRepositoryImpl implements TaskRepository {
    private final SqlTaskRepository repository;

    TaskRepositoryImpl(final SqlTaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Task> findById(final Integer id) {
        return repository.findById(id).map(Task::restore);
    }

    @Override
    public Task save(final Task entity) {
        return Task.restore(repository.save(entity.getSnapshot()));
    }

    @Override
    public List<Task> saveAll(final Iterable<Task> entities) {
        return repository.saveAll(StreamSupport.stream(entities.spliterator(), false)
                .map(Task::getSnapshot)
                .collect(Collectors.toList())
        ).stream()
                .map(Task::restore)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(final Integer id) {
        repository.deleteById(id);
    }
}

interface SqlTaskQueryRepository extends TaskQueryRepository, Repository<TaskSnapshot, Integer> {
}
@org.springframework.stereotype.Repository
class TaskQueryRepositoryImpl implements TaskQueryRepository {
    private final SqlTaskQueryRepository taskQueryRepository;

    TaskQueryRepositoryImpl(final SqlTaskQueryRepository taskQueryRepository) {
        this.taskQueryRepository = taskQueryRepository;
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public Optional<TaskDto> findDtoById(final int id) {
        return Optional.empty();
    }

    @Override
    public boolean existsByDoneIsFalseAndProject_Id(final int id) {
        return false;
    }

    @Override
    public <T> Set<T> findBy(final Class<T> type) {
        return null;
    }
}

