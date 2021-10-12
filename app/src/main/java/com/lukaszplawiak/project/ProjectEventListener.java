package com.lukaszplawiak.project;

import com.lukaszplawiak.task.vo.TaskEvent;
import org.springframework.context.event.EventListener;

class ProjectEventListener {
    private final ProjectFacade facade;

    ProjectEventListener(final ProjectFacade facade) {
        this.facade = facade;
    }

    @EventListener
    // warning: must be synchronous in current design
    public void on(TaskEvent event) {
        facade.handle(event);
    }
}
