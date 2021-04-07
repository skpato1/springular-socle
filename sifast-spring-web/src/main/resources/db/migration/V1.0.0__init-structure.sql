create table IF NOT EXISTS oauth_client_details
(
    client_id               VARCHAR(256) PRIMARY KEY,
    resource_ids            VARCHAR(256),
    client_secret           VARCHAR(256),
    scope                   VARCHAR(256),
    authorized_grant_types  VARCHAR(256),
    web_server_redirect_uri VARCHAR(256),
    authorities             VARCHAR(256),
    access_token_validity   INTEGER,
    refresh_token_validity  INTEGER,
    additional_information  VARCHAR(4096),
    autoapprove             VARCHAR(256)
);

create table IF NOT EXISTS oauth_client_token
(
    token_id          VARCHAR(256),
    token             BLOB,
    authentication_id VARCHAR(256) PRIMARY KEY,
    user_name         VARCHAR(256),
    client_id         VARCHAR(256)
);

create table IF NOT EXISTS oauth_access_token
(
    token_id          VARCHAR(256),
    token             BLOB,
    authentication_id VARCHAR(256) PRIMARY KEY,
    user_name         VARCHAR(256),
    client_id         VARCHAR(256),
    authentication    BLOB,
    refresh_token     VARCHAR(256)
);

create table IF NOT EXISTS oauth_refresh_token
(
    token_id       VARCHAR(256),
    token          BLOB,
    authentication BLOB
);

create table IF NOT EXISTS oauth_code
(
    code           VARCHAR(256),
    authentication BLOB
);

create table IF NOT EXISTS user
(
    id INTEGER PRIMARY KEY,
    first_name VARCHAR(256),
    last_name VARCHAR(256),
    login VARCHAR(256),
    password VARCHAR(256),
    enabled BOOLEAN,
    is_deleted BOOLEAN
);

CREATE TABLE IF NOT EXISTS `authority` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `creation_date` datetime DEFAULT NULL,
    `update_date` datetime DEFAULT NULL,
    `description` varchar(255) DEFAULT NULL,
    `designation` varchar(255) DEFAULT NULL,
    `is_deleted` bit(1) DEFAULT NULL,
    `is_displayed` bit(1) NOT NULL,
    `parent_id` int(11) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKlelfu0wsop8w6ji7qgiyt5chq` (`parent_id`),
    CONSTRAINT `FKlelfu0wsop8w6ji7qgiyt5chq` FOREIGN KEY (`parent_id`) REFERENCES `authority` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `generic_track` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `creation_date` datetime DEFAULT NULL,
    `update_date` datetime DEFAULT NULL,
    `all_changed_values` text,
    `changed_properties` text,
    `changed_state` text,
    `entity_id` int(11) DEFAULT NULL,
    `entity_name` varchar(255) DEFAULT NULL,
    `event_date` datetime DEFAULT NULL,
    `event_type` varchar(255) DEFAULT NULL,
    `is_deleted` bit(1) DEFAULT NULL,
    `performed_by` varchar(255) DEFAULT NULL,
    `previous_state` text,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `role` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `creation_date` datetime DEFAULT NULL,
    `update_date` datetime DEFAULT NULL,
    `description` varchar(255) DEFAULT NULL,
    `designation` varchar(255) DEFAULT NULL,
    `is_deleted` bit(1) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `role_authority` (
    `role_id` int(11) NOT NULL,
    `authority_id` int(11) NOT NULL,
    PRIMARY KEY (`role_id`,`authority_id`),
    KEY `FKqbri833f7xop13bvdje3xxtnw` (`authority_id`),
    CONSTRAINT `FK2052966dco7y9f97s1a824bj1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
    CONSTRAINT `FKqbri833f7xop13bvdje3xxtnw` FOREIGN KEY (`authority_id`) REFERENCES `authority` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `user_role` (
    `user_id` int(11) NOT NULL,
    `role_id` int(11) NOT NULL,
    PRIMARY KEY (`user_id`,`role_id`),
    KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
    CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
