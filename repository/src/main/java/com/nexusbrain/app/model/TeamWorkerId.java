package com.nexusbrain.app.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TeamWorkerId implements Serializable {

    @Column(name = "team_id")
    private Long teamId;

    @Column(name = "worker_id")
    private Long workerId;

    TeamWorkerId() {}

    public TeamWorkerId(Long teamId, Long workerId) {
        this.teamId = teamId;
        this.workerId = workerId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Long workerId) {
        this.workerId = workerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TeamWorkerId that = (TeamWorkerId) o;
        return Objects.equals(teamId, that.teamId) &&
                Objects.equals(workerId, that.workerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamId, workerId);
    }
}
