INSERT INTO `users` (username, password, enabled, name, surname, email) VALUES ('adrilope', '$2a$10$oJNmI4pc8kF9LXZFiOZ5..HRl7q0K8GbDCiqOLjbacieHY.21gxD2', 1, 'Adri', 'Lodeiro', 'adrilope@mail.com');
INSERT INTO `users` (username, password, enabled, name, surname, email) VALUES ('admin', '$2a$10$pb5nBoAvPjfig7aHC1JQZ.JgC09LORcr8ggAPih9.MbVldgd8PISu', 1, 'Jhon', 'Doe', 'admin@mail.com');

INSERT INTO `roles` (name) VALUES ('ROLE_USER');
INSERT INTO `roles` (name) VALUES ('ROLE_ADMIN');

INSERT INTO `users_roles` (user_id, role_id) VALUES (1, 1);
INSERT INTO `users_roles` (user_id, role_id) VALUES (2, 2);
INSERT INTO `users_roles` (user_id, role_id) VALUES (2, 1);