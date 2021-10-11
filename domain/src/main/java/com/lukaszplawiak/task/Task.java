package com.lukaszplawiak.task;

import com.lukaszplawiak.project.dto.SimpleProject;

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
                snapshot.getProject() == null ? null : SimpleProject.restore(snapshot.getProject())
        );
    }
    private int id;
    private String description;
    private boolean done;
    private ZonedDateTime deadline;
    private int changesCount;
    private String additionalComment;
    private SimpleProject project;

    protected Task(final int id, final String description, final boolean done, final ZonedDateTime deadline, final int changesCount, final String additionalComment, final SimpleProject project) {
        this.id = id;
        this.description = description;
        this.done = done;
        this.deadline = deadline;
        this.changesCount = changesCount;
        this.additionalComment = additionalComment;
        this.project = project;
    }

    void toggle() {
        done = !done;
        ++changesCount;
    }

    void updateInfo(final String description,final ZonedDateTime deadline,final String additionalComment) {
        this.description = description;
        this.deadline = deadline;
        this.additionalComment = additionalComment;
    }

    TaskSnapshot getSnapshot() {
        return new TaskSnapshot(
                id,
                description,
                done,
                deadline,
                changesCount,
                additionalComment,
                project == null ? null : project.getSnapshot()
        );
    }

}
