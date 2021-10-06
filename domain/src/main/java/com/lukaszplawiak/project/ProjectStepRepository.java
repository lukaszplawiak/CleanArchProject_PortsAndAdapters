package com.lukaszplawiak.project;

import org.springframework.data.repository.Repository;

interface ProjectStepRepository extends Repository<ProjectStep, Integer> {
    void delete(ProjectStep entity);
}
