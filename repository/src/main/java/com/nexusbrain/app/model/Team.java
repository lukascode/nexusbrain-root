package com.nexusbrain.app.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TeamWorker> workers = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    Team() {}

    public Team(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void addWorker(Worker worker) {
        TeamWorker teamWorker = new TeamWorker(this, worker);
        workers.add(teamWorker);
        worker.getTeams().add(teamWorker);
    }

    public boolean hasWorker(Worker worker) {
        TeamWorker teamWorker = new TeamWorker(this, worker);
        return workers.contains(teamWorker);
    }

    public void removeWorker(Worker worker) {
        for (Iterator<TeamWorker> iterator = workers.iterator(); iterator.hasNext(); ) {
            TeamWorker teamWorker = iterator.next();
            if (teamWorker.getTeam().equals(this) &&
                    teamWorker.getWorker().equals(worker)) {
                iterator.remove();
                teamWorker.getWorker().getTeams().remove(teamWorker);
                teamWorker.setTeam(null);
                teamWorker.setWorker(null);
            }
        }
    }

    public void removeWorkers() {
        for (TeamWorker teamWorker: workers) {
            teamWorker.getWorker().getTeams().remove(teamWorker);
            teamWorker.setTeam(null);
            teamWorker.setWorker(null);
        }
        workers.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Team team = (Team) o;
        return Objects.equals(id, team.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean hasProject() {
        return project != null;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Set<TeamWorker> getWorkers() {
        return workers;
    }

    public int getNumberOfWorkers() {
        return workers.size();
    }

    public Project getProject() {
        return project;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
