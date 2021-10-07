package com.lukaszplawiak.project;

import com.lukaszplawiak.project.dto.ProjectDto;

import java.util.List;
import java.util.Optional;

public interface ProjectQueryRepository {

    Optional<ProjectDto> findDtoById(Integer integer);

    List<ProjectDto> findBy();

    long count();
}
