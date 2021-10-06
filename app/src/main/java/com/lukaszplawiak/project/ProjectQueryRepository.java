package com.lukaszplawiak.project;

import com.lukaszplawiak.project.dto.ProjectDto;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface ProjectQueryRepository extends Repository<Project, Integer> {

    Optional<ProjectDto> findDtoById(Integer integer);

    List<ProjectDto> findBy();

    long count();
}
