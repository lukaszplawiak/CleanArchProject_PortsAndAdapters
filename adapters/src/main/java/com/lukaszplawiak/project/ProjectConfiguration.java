package com.lukaszplawiak.project;

import com.lukaszplawiak.task.TaskFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ProjectConfiguration {
    @Bean
    ProjectFacade projectFacade(
            ProjectRepository projectRepository,
            TaskFacade taskFacade

    ) {
        return new ProjectFacade(
                new ProjectFactory(),
                projectRepository,
                taskFacade
        );
    }
}
