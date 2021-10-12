package com.lukaszplawiak.task.vo;

import com.lukaszplawiak.DomainEvent;

import java.time.Instant;
import java.time.ZonedDateTime;

//value object niesie ze soba informacje(obiekty tyou vo sa tworzone tylko po to aby "w sobie"(sobą) nieść jakąś informację)
public class TaskEvent implements DomainEvent {
    public enum State { // stan zdarzenia
        DONE, UNDONE, UPDATED, DELETED // rodzaje zdazen ktore bedziemy chciali obslugiwac
    }

    private final Instant occurredOn;
    private final TaskSourceId id;
    private final State state;
    private final Data date;

    public TaskEvent(final TaskSourceId id, final State state, final Data date) {
        this.occurredOn = Instant.now();
        this.id = id;
        this.state = state;
        this.date = date;
    }

    @Override
    public Instant getOccurredOn() {
        return occurredOn;
    }

    public TaskSourceId getId() {
        return id;
    }

    public State getState() {
        return state;
    }

    public Data getDate() {
        return date;
    }

    public static class Data {
        private final String description;
        private final ZonedDateTime deadline;
        private final String additionalComment;

        public Data(final String description, final ZonedDateTime deadline, final String additionalComment) {
            this.description = description;
            this.deadline = deadline;
            this.additionalComment = additionalComment;
        }

        public String getDescription() {
            return description;
        }

        public ZonedDateTime getDeadline() {
            return deadline;
        }

        public String getAdditionalComment() {
            return additionalComment;
        }
    }
}
