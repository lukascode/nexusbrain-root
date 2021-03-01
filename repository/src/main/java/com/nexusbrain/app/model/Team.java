package com.nexusbrain.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    @Column
    private String name;

    @OneToMany(cascade = {PERSIST, MERGE, REFRESH, DETACH}, mappedBy = "team")
    private List<Worker> workers = new ArrayList<>();

    Team() {}

    public Team(String name) {
        this.name = name;
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


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
