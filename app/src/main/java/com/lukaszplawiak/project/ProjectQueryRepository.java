package com.lukaszplawiak.project;

import com.lukaszplawiak.project.dto.ProjectDto;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProjectQueryRepository {
    Optional<ProjectDto> findDtoById(Integer id);

    <T> Set<T> findBy(Class<T> type);

    long count();
}
