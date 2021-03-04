package com.nexusbrain.app.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "team_worker")
public class TeamWorker {

    @EmbeddedId
    private TeamWorkerId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("teamId")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("workerId")
    private Worker worker;

    TeamWorker() {}

    public TeamWorker(Team team, Worker worker) {
        Objects.requireNonNull(team);
        Objects.requireNonNull(worker);
        this.team = team;
        this.worker = worker;
        this.id = new TeamWorkerId(team.getId(), worker.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TeamWorker that = (TeamWorker) o;
        return Objects.equals(team, that.team) &&
                Objects.equals(worker, that.worker);
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, worker);
    }

    public Team getTeam() {
        return team;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public TeamWorkerId getId() {
        return id;
    }
}
