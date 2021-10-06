package com.lukaszplawiak.project;

import org.springframework.data.repository.Repository;

import java.util.Optional;

interface ProjectRepository extends Repository<Project, Integer> {
    <S extends Project> S save(S entity);

    Optional<Project> findById(Integer integer);
}
