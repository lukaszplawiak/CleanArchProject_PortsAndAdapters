package com.lukaszplawiak.task;

import com.lukaszplawiak.task.dto.TaskDto;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

interface SqlTaskRepository extends Repository<SqlTask, Integer> {
    Optional<SqlTask> findById(Integer id); // metoda ktora odzcytuje tylko pojedynczy task oraz tak która zapisuje pojedynczy task tp sa dwie rzeczy które zostawiamy w tym repo - to jeset repo do zapisu

    SqlTask save(SqlTask entity);

    List<SqlTask> saveAll(Iterable<SqlTask> entities);

    void deleteById(Integer id);
}
@org.springframework.stereotype.Repository
class TaskRepositoryImpl implements TaskRepository {
    private final SqlTaskRepository sqlTaskRepository;

    TaskRepositoryImpl(final SqlTaskRepository sqlTaskRepository) {
        this.sqlTaskRepository = sqlTaskRepository;
    }

    @Override
    public Optional<Task> findById(final Integer id) {
        return sqlTaskRepository.findById(id).map(SqlTask::toTask);
    }

    @Override
    public Task save(final Task entity) {
        return sqlTaskRepository.save(SqlTask.fromTask(entity)).toTask();
    }

    @Override
    public List<Task> saveAll(final Iterable<Task> entities) {
        return sqlTaskRepository.saveAll(StreamSupport.stream(entities.spliterator(), false)
                .map(SqlTask::fromTask)
                .collect(Collectors.toList())
        ).stream()
                .map(SqlTask::toTask)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(final Integer id) {
        sqlTaskRepository.deleteById(id);
    }
}

interface SqlTaskQueryRepository extends TaskQueryRepository, Repository<SqlTask, Integer> {
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

