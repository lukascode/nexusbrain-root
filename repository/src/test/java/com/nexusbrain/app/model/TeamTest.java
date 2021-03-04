package com.nexusbrain.app.model;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

public class TeamTest {

    @Test
    public void shouldCreateTeamTest() {
        // when
        Team team = new Team("Team", "Team test");

        // then
        Assertions.assertThat(team.getName()).isEqualTo("Team");
        Assertions.assertThat(team.getDescription()).isEqualTo("Team test");
        Assertions.assertThat(team.hasProject()).isFalse();
        Assertions.assertThat(team.getNumberOfWorkers()).isEqualTo(0);
        Assertions.assertThat(team.getId()).isNull();
    }

    @Test
    public void shouldAddWorkerTest() {
        // given
        Worker worker = new Worker();
        Team team = prepareTeam();
        int workers = team.getNumberOfWorkers();

        // when
        team.addWorker(worker);

        // then
        Assertions.assertThat(team.getNumberOfWorkers()).isEqualTo(workers + 1);
        Assertions.assertThat(team.hasWorker(worker));
    }

    @Test
    public void shouldAddWorkerToMultipleTeams() {
        // given
        Team team1 = new Team();
        team1.setId(1L);
        Team team2 = new Team();
        team2.setId(2L);
        Worker worker = new Worker();
        worker.setId(1L);

        // when
        team1.addWorker(worker);
        team2.addWorker(worker);

        // then
        Assertions.assertThat(team1.hasWorker(worker)).isTrue();
        Assertions.assertThat(team1.getNumberOfWorkers()).isEqualTo(1);
        Assertions.assertThat(team2.hasWorker(worker)).isTrue();
        Assertions.assertThat(team2.getNumberOfWorkers()).isEqualTo(1);
    }

    @Test
    public void shouldRemoveWorkerTest() {
        // given
        Team team = prepareTeam();
        Worker worker = new Worker();
        worker.setId(55L);
        team.addWorker(worker);
        Assertions.assertThat(team.hasWorker(worker)).isTrue();
        int workers = team.getNumberOfWorkers();

        // when
        team.removeWorker(worker);

        // then
        Assertions.assertThat(team.hasWorker(worker)).isFalse();
        Assertions.assertThat(team.getNumberOfWorkers()).isEqualTo(workers - 1);
    }

    @Test
    public void shouldRemoveAllWorkersTest() {
        // given
        Team team1 = new Team();
        team1.setId(1L);
        Team team2 = new Team();
        team2.setId(2L);

        Worker worker1 = new Worker();
        worker1.setId(1L);
        Worker worker2 = new Worker();
        worker2.setId(2L);

        team1.addWorker(worker1);
        team1.addWorker(worker2);
        team2.addWorker(worker1);
        team2.addWorker(worker2);

        Assertions.assertThat(team1.getNumberOfWorkers()).isEqualTo(2);
        Assertions.assertThat(team2.getNumberOfWorkers()).isEqualTo(2);

        // when
        team1.removeWorkers();

        // then
        Assertions.assertThat(team1.getNumberOfWorkers()).isEqualTo(0);
        Assertions.assertThat(team2.getNumberOfWorkers()).isEqualTo(2);
    }

    @Test
    public void equalsAndHashCodeTest() {
        Team team1 = new Team();
        team1.setId(1L);
        Team team2 = new Team();
        team2.setId(1L);
        Team team3 = new Team();
        team3.setId(2L);

        Assertions.assertThat(team1.equals(team2)).isTrue();
        Assertions.assertThat(team1.hashCode()).isEqualTo(team2.hashCode());
        Assertions.assertThat(team1.equals(team3)).isFalse();
        Assertions.assertThat(team1.hashCode()).isNotEqualTo(team3.hashCode());
    }

    private Team prepareTeam() {
        Worker worker1 = new Worker();
        worker1.setId(1L);
        Worker worker2 = new Worker();
        worker2.setId(2L);
        Team team = new Team();
        team.setId(1L);
        team.addWorker(worker1);
        team.addWorker(worker2);
        return team;
    }
}
