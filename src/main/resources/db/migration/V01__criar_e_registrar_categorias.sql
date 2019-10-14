CREATE TABLE categoria(
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO categoria (nome) values ('GINECOLOGICA');
INSERT INTO categoria (nome) values ('EMERGENCIA');
INSERT INTO categoria (nome) values ('ODONTOLOGIA');
INSERT INTO categoria (nome) values ('OUTRAS');