DROP DATABASE IF EXISTS AgeRoyale;
CREATE DATABASE AgeRoyale;
USE AgeRoyale;

CREATE USER IF NOT EXISTS 'AgeRoyale10'@'localhost' IDENTIFIED BY 'AgeRoyale10';
GRANT ALL PRIVILEGES ON * . * TO 'AgeRoyale10'@'localhost';

DROP TABLE IF EXISTS User CASCADE;
CREATE TABLE User(
  id_user int NOT NULL,
  name text,
  mail text,
  password text,
  banned int,
  bannedDate long,
  PRIMARY KEY (id_user)
);

DROP TABLE IF EXISTS Game CASCADE;
CREATE TABLE Game (
  id_game int NOT NULL,
  gameTime int,
  date text,
  winner int,
  creatorUser int,
  joinedUser int,
  gameName text,
  privada boolean,
  PRIMARY KEY (id_game),
  FOREIGN KEY(creatorUser) REFERENCES User(id_user),
  FOREIGN KEY(joinedUser) REFERENCES User(id_user)
);


DROP TABLE IF EXISTS Follow CASCADE;
CREATE TABLE Follow (
  creatorUser int NOT NULL,
  joinedUser int NOT NULL,
  FOREIGN KEY(creatorUser) REFERENCES User(id_user),
  FOREIGN KEY(joinedUser) REFERENCES User(id_user)
);

DROP TABLE IF EXISTS Request CASCADE;
CREATE TABLE Request (
  fromUser int NOT NULL,
  toUser int NOT NULL,
  FOREIGN KEY(fromUser) REFERENCES User(id_user),
  FOREIGN KEY(toUser) REFERENCES User(id_user)
);

INSERT INTO User(id_user,name,mail,password) VALUES ('1','laia','l@b.c','Ll123456','0');
INSERT INTO User(id_user,name,mail,password) VALUES ('2','l','l@b.cm','Ll123456','0');
INSERT INTO User(id_user,name,mail,password) VALUES ('3','a','lae@b.cm','Ll123456','0');
INSERT INTO User(id_user,name,mail,password) VALUES ('4','b','leb@b.cm','Ll123456','0');
INSERT INTO User(id_user,name,mail,password) VALUES ('5','c','lec@b.cm','Ll123456','0');
INSERT INTO User(id_user,name,mail,password) VALUES ('6','d','led@b.cm','Ll123456','0');
INSERT INTO User(id_user,name,mail,password) VALUES ('7','e','lee@b.cm','Ll123456','0');
INSERT INTO User(id_user,name,mail,password) VALUES ('8','f','lef@b.cm','Ll123456','0');
INSERT INTO User(id_user,name,mail,password) VALUES ('9','g','leg@b.cm','Ll123456','0');
INSERT INTO User(id_user,name,mail,password) VALUES ('10','h','leh@b.cm','Ll123456','0');
INSERT INTO User(id_user,name,mail,password) VALUES ('11','i','lei@b.cm','Ll123456','0');




INSERT INTO Game(id_game,gameTime,date,winner,creatorUser,joinedUser,gameName,privada) VALUES ('1','30','2020-06-21','1','1','2','gg','0');
INSERT INTO Game(id_game,gameTime,date,winner,creatorUser,joinedUser,gameName,privada) VALUES ('2','20','2020-06-21','2','2','1','gg','0');
INSERT INTO Game(id_game,gameTime,date,winner,creatorUser,joinedUser,gameName,privada) VALUES ('3','10','2020-06-25','3','3','2','gg','0');
INSERT INTO Game(id_game,gameTime,date,winner,creatorUser,joinedUser,gameName,privada) VALUES ('4','30','2020-05-21','4','4','2','gg','0');
INSERT INTO Game(id_game,gameTime,date,winner,creatorUser,joinedUser,gameName,privada) VALUES ('5','56','2020-05-21','5','5','6','gg','0');
INSERT INTO Game(id_game,gameTime,date,winner,creatorUser,joinedUser,gameName,privada) VALUES ('6','45','2020-05-29','6','6','2','gg','0');
INSERT INTO Game(id_game,gameTime,date,winner,creatorUser,joinedUser,gameName,privada) VALUES ('7','33','2019-07-22','7','7','5','gg','0');
INSERT INTO Game(id_game,gameTime,date,winner,creatorUser,joinedUser,gameName,privada) VALUES ('8','23','2019-10-22','8','8','2','gg','0');
INSERT INTO Game(id_game,gameTime,date,winner,creatorUser,joinedUser,gameName,privada) VALUES ('9','34','2019-10-22','9','9','2','gg','0');
INSERT INTO Game(id_game,gameTime,date,winner,creatorUser,joinedUser,gameName,privada) VALUES ('10','12','2019-10-22','10','10','2','gg','0');
INSERT INTO Game(id_game,gameTime,date,winner,creatorUser,joinedUser,gameName,privada) VALUES ('11','33','2019-07-22','7','7','5','gg','0');
INSERT INTO Game(id_game,gameTime,date,winner,creatorUser,joinedUser,gameName,privada) VALUES ('12','23','2020-06-22','8','8','2','gg','0');
INSERT INTO Game(id_game,gameTime,date,winner,creatorUser,joinedUser,gameName,privada) VALUES ('13','34','2020-06-22','10','10','9','gg','0');
INSERT INTO Game(id_game,gameTime,date,winner,creatorUser,joinedUser,gameName,privada) VALUES ('14','12','2020-06-22','10','10','2','gg','0');

SELECT * FROM Game;

SELECT COUNT(id_user) FROM User;

SELECT SUM(totalCreator + totalJoined) DIV 2, id_user FROM (SELECT COUNT(creatorUser) AS totalCreator, creatorUser, id_game FROM Game GROUP BY creatorUser, id_game) AS t1, 
(SELECT COUNT(joinedUser) AS totalJoined, joinedUser, id_game FROM Game GROUP BY joinedUser, id_game) AS t2, User
WHERE t2.id_game = t1.id_game AND (User.id_user = t1.creatorUser OR User.id_user = t2.joinedUser) GROUP BY 2;

SELECT COUNT(winner), winner, SUM(gameTime) FROM Game GROUP BY winner ORDER BY 1 DESC LIMIT 10;
