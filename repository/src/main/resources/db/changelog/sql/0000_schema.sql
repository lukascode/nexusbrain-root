-- liquibase formatted sql
-- changeset nb:0000

CREATE TABLE `projects`
(
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT,
    `description` varchar(255) DEFAULT NULL,
    `name`        varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `teams`
(
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT,
    `description` varchar(255) DEFAULT NULL,
    `name`        varchar(255) NOT NULL,
    `project_id`  bigint(20)   DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `project_fk_constraint` (`project_id`),
    CONSTRAINT `project_fk_constraint` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `workers`
(
    `id`        bigint(20)   NOT NULL AUTO_INCREMENT,
    `email`     varchar(255) NOT NULL,
    `full_name` varchar(255) NOT NULL,
    `team_id`   bigint(20) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `team_fk_constraint` (`team_id`),
    CONSTRAINT `team_fk_constraint` FOREIGN KEY (`team_id`) REFERENCES `teams` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- rollback DROP TABLE `workers`
-- rollback DROP TABLE `teams`
-- rollback DROP TABLE `projects`