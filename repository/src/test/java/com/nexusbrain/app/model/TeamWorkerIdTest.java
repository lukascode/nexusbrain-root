package com.nexusbrain.app.model;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

public class TeamWorkerIdTest {

    @Test
    public void shouldCreateIdTest() {
        // when
        TeamWorkerId id = new TeamWorkerId(1L, 2L);

        // then
        Assertions.assertThat(id.getTeamId()).isEqualTo(1L);
        Assertions.assertThat(id.getWorkerId()).isEqualTo(2L);
    }

    @Test
    public void equalsAndHashCodeTest() {
        TeamWorkerId id1 = new TeamWorkerId(1L, 2L);
        TeamWorkerId id2 = new TeamWorkerId(1L, 2L);
        TeamWorkerId id3 = new TeamWorkerId(1L, 3L);

        Assertions.assertThat(id1.equals(id2)).isTrue();
        Assertions.assertThat(id1.hashCode()).isEqualTo(id2.hashCode());
        Assertions.assertThat(id1.equals(id3)).isFalse();
        Assertions.assertThat(id1.hashCode()).isNotEqualTo(id3.hashCode());
    }

}
