package com.lukaszplawiak.task;

import com.lukaszplawiak.task.vo.TaskCreator;
import com.lukaszplawiak.task.vo.TaskEvent;
import com.lukaszplawiak.task.vo.TaskSourceId;

import java.time.ZonedDateTime;


class Task {
    static Task restore(TaskSnapshot snapshot) {
        return new Task(
                snapshot.getId(),
                snapshot.getDescription(),
                snapshot.isDone(),
                snapshot.getDeadline(),
                snapshot.getChangesCount(),
                snapshot.getAdditionalComment(),
                snapshot.getSourceId()
        );
    }

    public static Task createFrom(final TaskCreator source) {
        return new Task(
                0,
                source.getDescription(),
                false,
                source.getDeadline(),
                0,
                null,
                source.getId()
        );
    }

    private int id;
    private String description;
    private boolean done;
    private ZonedDateTime deadline;
    private int changesCount;
    private String additionalComment;
    private TaskSourceId sourceId;

    protected Task(final int id, final String description, final boolean done, final ZonedDateTime deadline, final int changesCount, final String additionalComment, final TaskSourceId sourceId) {
        this.id = id;
        this.description = description;
        this.done = done;
        this.deadline = deadline;
        this.changesCount = changesCount;
        this.additionalComment = additionalComment;
        this.sourceId = sourceId;
    }

    TaskSnapshot getSnapshot() {
        return new TaskSnapshot(
                id,
                description,
                done,
                deadline,
                changesCount,
                additionalComment,
                sourceId
        );
    }

    TaskEvent toggle() {
        done = !done;
        ++changesCount;
        return new TaskEvent(
                sourceId,
                done ? TaskEvent.State.DONE : TaskEvent.State.UNDONE,
                null
        );
    }

    TaskEvent updateInfo(final String description,final ZonedDateTime deadline,final String additionalComment) {
        this.description = description;
        this.deadline = deadline;
        this.additionalComment = additionalComment;
        return new TaskEvent(
                sourceId,
                TaskEvent.State.UPDATED,
                new TaskEvent.Data(description, deadline, additionalComment)
        );
    }
}
