INSERT INTO `users` (username, password, enabled, name, surname, email) VALUES ('adrilope', '12345', 1, 'Adri', 'Lodeiro', 'adrilope@mail.com');
INSERT INTO `users` (username, password, enabled, name, surname, email) VALUES ('admin', '12345', 1, 'Jhon', 'Doe', 'admin@mail.com');

INSERT INTO `roles` (name) VALUES ('ROLE_USER');
INSERT INTO `roles` (name) VALUES ('ROLE_ADMIN');

INSERT INTO `users_roles` (user_id, role_id) VALUES (1, 1);
INSERT INTO `users_roles` (user_id, role_id) VALUES (2, 2);
INSERT INTO `users_roles` (user_id, role_id) VALUES (2, 1);