# --- !Ups

INSERT INTO Level VALUES (1,  'Newbie',              'Newbie.gif');
INSERT INTO Level VALUES (2,  'Pupil',               'Pupil.png');
INSERT INTO Level VALUES (3,  'Teen',                'Teen.png');
INSERT INTO Level VALUES (4,  'Adult',               'Adult.png');
INSERT INTO Level VALUES (5,  'Expert',              'Expert.png');
INSERT INTO Level VALUES (6,  'Master',              'Master.png');
INSERT INTO Level VALUES (7,  'Professional',        'Professional.png');
INSERT INTO Level VALUES (8,  'Leader',              'Leader.png');
INSERT INTO Level VALUES (9,  'Guru',                'Guru.png');
INSERT INTO Level VALUES (10, 'Idol',                'Idol.png');
INSERT INTO Level VALUES (11, 'Higher Intelligence', 'HigherIntelligence.png');
INSERT INTO Level VALUES (12, 'God',                 'God.png');



INSERT INTO Profile (email, password, first_name, last_name, gender) 
          VALUES ('cndr.ip@gmail.com', '0aa371f7f51bd1312cef02e827f35122c46aa011', 'Idel', 'Pivnitskiy', 'M');
INSERT INTO Profile (email, password, first_name, last_name, gender) 
          VALUES ('patric@mail.ru', '0aa371f7f51bd1312cef02e827f35122c46aa011', 'Patric', 'Grey', 'M');
INSERT INTO Profile (email, password, first_name, last_name, gender) 
          VALUES ('eva@mail.ru', '0aa371f7f51bd1312cef02e827f35122c46aa011', 'Eva', 'Jones', 'F');



INSERT INTO Task (title, level)
          VALUES ('Write BREATH by your left hand. It should be nice', 1);
INSERT INTO Task (title, level)
          VALUES ('Cut paper pigeon', 1);
INSERT INTO Task (title, level)
          VALUES ('Run a Chinese lantern', 1);
INSERT INTO Task (title, level)
          VALUES ('Find the girl with three orange items', 2);
INSERT INTO Task (title, level)
          VALUES ('Draw a girl on the box of pizza', 2);
INSERT INTO Task (title, level)
          VALUES ('Fires sausages on the fire with your friends', 2);
INSERT INTO Task (title, level)
          VALUES ('Find the energy drink and show how it makes you fly', 3);
INSERT INTO Task (title, level)
          VALUES ('Make a photo between 2 girls with big boobs =)', 3);
INSERT INTO Task (title, level)
          VALUES ('Shake the hand of a police officer', 3);


# --- !Downs

DELETE FROM Profile;

DELETE FROM Level;
