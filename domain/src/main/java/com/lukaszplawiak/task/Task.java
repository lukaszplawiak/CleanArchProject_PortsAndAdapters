package com.lukaszplawiak.task;

import com.lukaszplawiak.project.dto.SimpleProjectQueryEntity;
import org.springframework.data.annotation.PersistenceConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "tasks")
class Task {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;
    @NotNull
    private String description;
    private boolean done;
    private ZonedDateTime deadline;
    private int changesCount;
    private String additionalComment;
    @ManyToOne
    @JoinColumn(name = "source_id")
    private SimpleProjectQueryEntity project;

    @PersistenceConstructor
    protected Task() {
    }

    Task(@NotNull String description, ZonedDateTime deadline, SimpleProjectQueryEntity project) {
        this.description = description;
        this.deadline = deadline;
        this.project = project;
    }

    int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    boolean isDone() {
        return done;
    }

    void setDone(boolean done) {
        this.done = done;
    }

    ZonedDateTime getDeadline() {
        return deadline;
    }

    void setDeadline(ZonedDateTime deadline) {
        this.deadline = deadline;
    }

    int getChangesCount() {
        return changesCount;
    }

    void setChangesCount(int changesCount) {
        this.changesCount = changesCount;
    }

    String getAdditionalComment() {
        return additionalComment;
    }

    void setAdditionalComment(String additionalComment) {
        this.additionalComment = additionalComment;
    }

    SimpleProjectQueryEntity getProject() {
        return project;
    }

    void setProject(SimpleProjectQueryEntity project) {
        this.project = project;
    }
}
