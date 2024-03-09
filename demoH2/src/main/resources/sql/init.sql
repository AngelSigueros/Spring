-- Script para poblar la base de datos H2 con datos de ejemplo

-- Datos de Grupo
INSERT INTO H2_GROUP (id, name) VALUES (1, 'Amigos');
INSERT INTO H2_GROUP (id, name) VALUES (2, 'Familia');
INSERT INTO H2_GROUP (id, name) VALUES (3, 'Trabajo');

-- Datos de Usuario
INSERT INTO H2_USER (id, H2_USERname, email, password, active, profile_picture) VALUES (1, 'John Doe', 'john.doe@gmail.com', '$2a$10$QOIvBkXlWZrYRt./nHN5He//4iPHt/7L3/Bz8j7rJt.', TRUE, '/profile-pictures/johndoe.jpg');
INSERT INTO H2_USER (id, H2_USERname, email, password, active, profile_picture) VALUES (2, 'Jane Smith', 'jane.smith@gmail.com', '$2a$10$QOIvBkXlWZrYRt./nHN5He//4iPHt/7L3/Bz8j7rJt.', TRUE, '/profile-pictures/janessmith.png');
INSERT INTO H2_USER (id, H2_USERname, email, password, active, profile_picture) VALUES (3, 'Alice Johnson', 'alice.johnson@gmail.com', '$2a$10$QOIvBkXlWZrYRt./nHN5He//4iPHt/7L3/Bz8j7rJt.', FALSE, NULL);

-- Datos de H2_POST
INSERT INTO H2_POST (id, title, content, image, date_created, author_id) VALUES (1, 'Primer H2_POST', 'Este es el contenido del primer H2_POST', '/H2_POSTs/image1.png', DATEADD('day', -30, SYSDATE()), 1);
INSERT INTO H2_POST (id, title, content, image, date_created, author_id) VALUES (2, 'Segundo H2_POST', 'Este es el contenido del segundo H2_POST', '/H2_POSTs/image2.png', DATEADD('day', -60, SYSDATE()), 2);
INSERT INTO H2_POST (id, title, content, image, date_created, author_id) VALUES (3, 'Tercer H2_POST', 'Este es el contenido del tercer H2_POST', '/H2_POSTs/image3.png', DATEADD('day', -90, SYSDATE()), 3);

-- Datos de H2_COMMENT
INSERT INTO H2_COMMENT (id, content, date_created, author_id, H2_POST_id) VALUES (1, 'Primer comentario', DATEADD('day', -30, SYSDATE()), 2, 1);
INSERT INTO H2_COMMENT (id, content, date_created, author_id, H2_POST_id) VALUES (2, 'Segundo comentario', DATEADD('day', -60, SYSDATE()), 3, 2);
INSERT INTO H2_COMMENT (id, content, date_created, author_id, H2_POST_id) VALUES (3, 'Tercer comentario', DATEADD('day', -90, SYSDATE()), 1, 3);