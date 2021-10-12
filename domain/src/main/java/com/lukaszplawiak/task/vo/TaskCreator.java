package com.lukaszplawiak.task.vo;

import java.time.ZonedDateTime;

//pakiet vo - value object
public class TaskCreator {  // nościk informacji potrzebny do stworzenia nowego zadania
    private final TaskSourceId id;
    private final String description;
    private final ZonedDateTime deadline;

    public TaskCreator(final TaskSourceId id, final String description, final ZonedDateTime deadline) {
        this.id = id;
        this.description = description;
        this.deadline = deadline;
    }

    public TaskSourceId getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public ZonedDateTime getDeadline() {
        return deadline;
    }
}
