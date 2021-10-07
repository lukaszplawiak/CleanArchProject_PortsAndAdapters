package com.lukaszplawiak.project;

import org.springframework.data.repository.Repository;

interface SqlProjectQueryRepository extends Repository<Project, Integer> {

}

interface SqlProjectRepository extends Repository<Project, Integer> {

}

interface SqlProjectStepRepository extends Repository<ProjectStep, Integer> {

}