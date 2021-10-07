package com.lukaszplawiak.task;

import com.lukaszplawiak.project.dto.SimpleProjectQueryEntity;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.jdbc.core.SqlReturnType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "tasks")
public class SqlTask {
    static SqlTask fromTask(Task source) {   // metoda fabrykujaca
        var result = new SqlTask();
        result.id = source.getId();
        result.description = source.getDescription();
        result.done = source.isDone();
        result.deadline = source.getDeadline();
        result.changesCount = source.getChangesCount();
        result.additionalComment = source.getAdditionalComment();
        result.project = source.getProject() != null
                ? new SimpleProjectQueryEntity(source.getProject().getId(), source.getProject().getName())
                : null;  // tutaj i powyzej linijka zabezpieczamy sie przed nullem
        return result;
    }
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
    protected SqlTask() {
    }

    Task toTask() {
        var result = new Task(description, deadline, project != null
                ? new SimpleProjectQueryEntity(project.getId(), project.getName())
                : null);
        result.setId(id);
        result.setDone(done);
        result.setChangesCount(changesCount);
        result.setAdditionalComment(additionalComment);
        return result;
    }

}
