package com.nexusbrain.app.model;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;

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

    @OneToMany(cascade = {PERSIST, MERGE, REFRESH, DETACH}, mappedBy = "team")
    private List<Worker> workers = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    Team() {}

    public Team(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void addWorker(Worker worker) {
        Objects.requireNonNull(worker);
        worker.setTeam(this);
        workers.add(worker);
    }

    public void removeWorkers() {
        for (Worker w: workers) {
            w.setTeam(null);
        }
        workers.clear();
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

    public List<Worker> getWorkers() {
        return workers;
    }

    public int getNumberOfWorkers() {
        return workers.size();
    }

    public Project getProject() {
        return project;
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
