package com.lukaszplawiak.task;

import com.lukaszplawiak.project.dto.SimpleProjectSnapshot;

import java.time.ZonedDateTime;

class TaskSnapshot {

    private int id;
    private String description;
    private boolean done;
    private ZonedDateTime deadline;
    private int changesCount;
    private String additionalComment;
    private SimpleProjectSnapshot project;

    protected TaskSnapshot() {
    }

    TaskSnapshot(final int id,
                        final String description,
                        final boolean done,
                        final ZonedDateTime deadline,
                        final int changesCount,
                        final String additionalComment,
                        final SimpleProjectSnapshot project
    ) {
        this.id = id;
        this.description = description;
        this.done = done;
        this.deadline = deadline;
        this.changesCount = changesCount;
        this.additionalComment = additionalComment;
        this.project = project;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return done;
    }

    public ZonedDateTime getDeadline() {
        return deadline;
    }

    public int getChangesCount() {
        return changesCount;
    }

    public String getAdditionalComment() {
        return additionalComment;
    }

    public SimpleProjectSnapshot getProject() {
        return project;
    }
}
