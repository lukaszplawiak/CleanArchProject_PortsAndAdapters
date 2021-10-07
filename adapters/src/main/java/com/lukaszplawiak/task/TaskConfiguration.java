package com.lukaszplawiak.task;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TaskConfiguration {

    @Bean
    TaskFacade taskFacade(final TaskRepository taskRepository) {
        return new TaskFacade(new TaskFactory(), taskRepository);
    }

}
