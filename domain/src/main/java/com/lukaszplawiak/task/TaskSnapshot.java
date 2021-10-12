package com.lukaszplawiak.task;

import com.lukaszplawiak.task.vo.TaskSourceId;

import java.time.ZonedDateTime;

class TaskSnapshot {

    private int id;
    private String description;
    private boolean done;
    private ZonedDateTime deadline;
    private int changesCount;
    private String additionalComment;
    private TaskSourceId sourceId;

    protected TaskSnapshot() {
    }

    TaskSnapshot(final int id,
                        final String description,
                        final boolean done,
                        final ZonedDateTime deadline,
                        final int changesCount,
                        final String additionalComment,
                        final TaskSourceId sourceId
    ) {
        this.id = id;
        this.description = description;
        this.done = done;
        this.deadline = deadline;
        this.changesCount = changesCount;
        this.additionalComment = additionalComment;
        this.sourceId = sourceId;
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

    public TaskSourceId getSourceId() {
        return sourceId;
    }
}
