package com.lukaszplawiak.task.vo;

public class TaskSourceId {  // to daje nam tozsamość twórcy zadania
    private String id;

    protected TaskSourceId() {
    }

    public TaskSourceId(final String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
