package com.lukaszplawiak.project.dto;

import java.time.ZonedDateTime;

public class ProjectDeadlineDto {
    private ZonedDateTime deadline;

    public ZonedDateTime getDeadline() {
        return deadline;
    }

    void setDeadline(ZonedDateTime deadline) {
        this.deadline = deadline;
    }  // settery moga byc pakietowe, wszytko bedzie dzialac, wystawianie dalnych bedzie dzialc
}
