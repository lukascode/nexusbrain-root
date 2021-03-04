package com.nexusbrain.app.model;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

public class TeamWorkerTest {

    @Test
    public void shouldCreateTeamWorkerTest() {
        // given
        Team team = new Team();
        team.setId(1L);
        Worker worker = new Worker();
        worker.setId(1L);

        // when
        TeamWorker teamWorker = new TeamWorker(team, worker);

        // then
        Assertions.assertThat(teamWorker.getTeam()).isNotNull();
        Assertions.assertThat(teamWorker.getWorker()).isNotNull();
        Assertions.assertThat(teamWorker.getId()).isNotNull();
        Assertions.assertThat(teamWorker.getId().getTeamId()).isEqualTo(1);
        Assertions.assertThat(teamWorker.getId().getWorkerId()).isEqualTo(1);
    }

    @Test
    public void equalsAndHashCodeTest() {
        Team team = new Team();
        team.setId(1L);
        Worker worker1 = new Worker();
        worker1.setId(1L);
        Worker worker2 = new Worker();
        worker2.setId(1L);
        Worker worker3 = new Worker();
        worker3.setId(2L);

        TeamWorker tw1 = new TeamWorker(team, worker1);
        TeamWorker tw2 = new TeamWorker(team, worker2);
        TeamWorker tw3 = new TeamWorker(team, worker3);

        Assertions.assertThat(tw1.equals(tw2)).isTrue();
        Assertions.assertThat(tw1.hashCode()).isEqualTo(tw2.hashCode());
        Assertions.assertThat(tw1.equals(tw3)).isFalse();
        Assertions.assertThat(tw1.hashCode()).isNotEqualTo(tw3.hashCode());
    }

}
