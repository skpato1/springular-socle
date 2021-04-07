INSERT IGNORE INTO `user` (`id`, `first_name`, `last_name`, `login`, `password`, `enabled`, `is_deleted`)
VALUES (1, 'admin', 'admin', 'admin', '$2a$10$DWl1srWXMn8dyI3ieh4Z5.vlsD7QqavULLtLDKVzRRy/CavYv9TxO', 1, 0);

INSERT IGNORE INTO `authority` (`id`,`creation_date`,`update_date`,`description`,`designation`,`is_displayed`,`parent_id`,`is_deleted`)  VALUES
(1,now(),NULL,'Gestion des utilisateurs','AUTH_MANAGE_USER',0,NULL,0),
(2,now(),NULL,'Ajouter un utilisateur','AUTH_CREATE_USER',1,1,0),
(3,now(),NULL,'Modifier un utilisateur','AUTH_UPDATE_USER',1,1,0),
(4,now(),NULL,'Supprimer un utilisateur','AUTH_DELETE_USER',1,1,0),
(5,now(),NULL,'Afficher les utilisateurs','AUTH_VIEW_USER',1,1,0),
(6,now(),NULL,'Gestion des rôles','AUTH_MANAGE_ROLE',0,NULL,0),
(7,now(),NULL,'Ajouter un rôle','AUTH_CREATE_ROLE',1,6,0),
(8,now(),NULL,'Modifier un rôle','AUTH_UPDATE_ROLE',1,6,0),
(9,now(),NULL,'Supprimer un rôle','AUTH_DELETE_ROLE',1,6,0),
(10,now(),NULL,'Afficher un rôle','AUTH_VIEW_ROLE',1,6,0),
(11,now(),NULL,'default authority','DEFAULT_AUTH',0,NULL,0);

INSERT IGNORE INTO `role`
VALUES (1, now(), NULL, 'ROLE_SUPER_ADMIN', 'ROLE_SUPER_ADMIN', 0);

INSERT IGNORE INTo `role_authority` (`role_id`,`authority_id`) VALUES (1,2), (1,3), (1,4),(1,5),(1,7),(1,8),(1,9),(1,10),(1,11);

INSERT IGNORE INTO `user_role` (`user_id`, `role_id`)
VALUES (1, 1);

INSERT IGNORE INTO oauth_client_details (client_id, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity,
                                         refresh_token_validity, additional_information, autoapprove)
VALUES ("client", "{bcrypt}$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG", "read,write,trust", "password,authorization_code,refresh_token", null, null, 3600, 72000,
        null, true);
