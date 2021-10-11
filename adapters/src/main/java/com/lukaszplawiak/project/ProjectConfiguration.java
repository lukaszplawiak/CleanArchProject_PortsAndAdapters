package com.lukaszplawiak.project;

import com.lukaszplawiak.task.TaskFacade;
import com.lukaszplawiak.task.TaskQueryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ProjectConfiguration {
    @Bean
    ProjectFacade projectFacade(
            ProjectRepository projectRepository,
            TaskFacade taskFacade,
            TaskQueryRepository taskQueryRepository
    ) {
        return new ProjectFacade(
                new ProjectFactory(),
                projectRepository,
                taskFacade,
                taskQueryRepository
        );
    }
}
