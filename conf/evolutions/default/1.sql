# --- !Ups

CREATE  TABLE user (
  id INT NOT NULL AUTO_INCREMENT,
  login VARCHAR(16) NOT NULL ,
  name VARCHAR(128) NOT NULL ,
  mail VARCHAR(128) NULL ,
  sex INT(1) NOT NULL ,
  PRIMARY KEY (id) 
);

# --- !Downs

DROP table user;
