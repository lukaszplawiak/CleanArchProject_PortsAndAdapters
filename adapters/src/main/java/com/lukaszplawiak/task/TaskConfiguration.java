package com.lukaszplawiak.task;

import com.lukaszplawiak.DomainEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TaskConfiguration {

    @Bean
    TaskFacade taskFacade(final TaskRepository taskRepository, DomainEventPublisher publisher) {
        return new TaskFacade(new TaskFactory(), taskRepository, publisher);
    }

}
