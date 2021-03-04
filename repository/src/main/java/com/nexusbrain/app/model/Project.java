package com.nexusbrain.app.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project", orphanRemoval = true)
    private List<Team> teams = new ArrayList<>();

    Project() {}

    public Project(String name, String description) {
        this.name = name;
        this.description = description;
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

    public List<Team> getTeams() {
        return teams;
    }

    public int getNumberOfTeams() {
        return teams.size();
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

    public void addTeam(Team team) {
        Objects.requireNonNull(team);
        team.setProject(this);
        teams.add(team);
    }

    public boolean hasTeam(Team team) {
        Objects.requireNonNull(team);
        for (Team t: teams) {
            if (t.equals(team)) {
                return true;
            }
        }
        return false;
    }

    public void removeTeam(Team team) {
        Objects.requireNonNull(team);
        for (Iterator<Team> iterator = teams.iterator(); iterator.hasNext(); ) {
            Team t = iterator.next();
            if (team.equals(t)) {
                iterator.remove();
                t.setProject(null);
                t.removeWorkers();
            }
        }
    }

    public void removeTeams() {
        for (Team t: teams) {
            t.setProject(null);
            t.removeWorkers();
        }
        teams.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;
        return Objects.equals(id, project.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
