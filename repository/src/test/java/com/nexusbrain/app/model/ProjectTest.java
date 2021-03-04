package com.nexusbrain.app.model;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectTest {

    @Test
    public void shouldCreateProjectTest() {
        // when
        Project project = new Project("Project", "Test project");

        // then
        Assertions.assertThat(project.getName()).isEqualTo("Project");
        Assertions.assertThat(project.getDescription()).isEqualTo("Test project");
        Assertions.assertThat(project.getNumberOfTeams()).isEqualTo(0);
    }

    @Test
    public void shouldAddTeamTest() {
        // given
        Project project = prepareProject();
        Team team = new Team();

        int teams = project.getNumberOfTeams();

        // when
        project.addTeam(team);

        // then
        Assertions.assertThat(project.hasTeam(team)).isTrue();
        Assertions.assertThat(project.getNumberOfTeams()).isEqualTo(teams + 1);
        Assertions.assertThat(team.getProject()).isEqualTo(project);
    }

    @Test
    public void shouldRemoveTeamTest() {
        // given
        Project project = prepareProject();
        Team team = new Team();
        team.setId(100L);
        project.addTeam(team);

        int teams = project.getNumberOfTeams();

        // when
        project.removeTeam(team);

        // then
        Assertions.assertThat(project.hasTeam(team)).isFalse();
        Assertions.assertThat(project.getNumberOfTeams()).isEqualTo(teams - 1);
        Assertions.assertThat(team.getProject()).isNull();
    }

    @Test
    public void shouldRemoveAllTeamsTest() {
        // given
        Project project = prepareProject();
        List<Team> teams = project.getTeams().stream().collect(Collectors.toList());

        // when
        project.removeTeams();

        // then
        Assertions.assertThat(project.getNumberOfTeams()).isEqualTo(0);
        for (Team t: teams) {
            Assertions.assertThat(t.getProject()).isNull();
        }
    }

    @Test
    public void equalsAndHashCodeTest() {
        Project project1 = new Project();
        project1.setId(1L);
        Project project2 = new Project();
        project2.setId(1L);
        Project project3 = new Project();
        project3.setId(2L);

        Assertions.assertThat(project1.equals(project2)).isTrue();
        Assertions.assertThat(project1.hashCode()).isEqualTo(project2.hashCode());
        Assertions.assertThat(project1.equals(project3)).isFalse();
        Assertions.assertThat(project1.hashCode()).isNotEqualTo(project3.hashCode());
    }

    private Project prepareProject() {
        Project project = new Project();
        Team team1 = new Team();
        team1.setId(1L);
        Team team2 = new Team();
        team2.setId(2L);
        project.addTeam(team1);
        project.addTeam(team2);
        return project;
    }
}