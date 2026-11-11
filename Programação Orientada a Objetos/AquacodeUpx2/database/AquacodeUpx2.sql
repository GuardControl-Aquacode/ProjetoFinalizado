CREATE DATABASE IF NOT EXISTS AquacodeUpx2;

USE AquacodeUpx2;

CREATE TABLE IF NOT EXISTS cliente(
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    telefone VARCHAR (15) NOT NULL,
    email VARCHAR (50)
);

CREATE TABLE IF NOT EXISTS local (
    id_local INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL,
    CONSTRAINT fk_local_cliente
    FOREIGN KEY (id_cliente) REFERENCES cliente (id_cliente), 
    nome_local VARCHAR(100) NOT NULL,
    descricao VARCHAR(100) NOT NULL,
    cidade VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS gestor (
   id_gestor INT AUTO_INCREMENT PRIMARY KEY,
   id_cliente INT NOT NULL,
   CONSTRAINT fk_gestor_cliente 
   FOREIGN KEY (id_cliente) REFERENCES cliente (id_cliente),
   nome VARCHAR(100) NOT NULL,
   telefone VARCHAR (15) NOT NULL,
   email VARCHAR (50)
);

CREATE TABLE IF NOT EXISTS seguranca(
	cpf_seguranca CHAR(11) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    telefone VARCHAR(15) NOT NULL,
    email VARCHAR(50),
    codigo_identificacao VARCHAR(30) NOT NULL UNIQUE,
    posto_principal VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS ronda(
    id_ronda INT AUTO_INCREMENT PRIMARY KEY,
    cpf_seguranca CHAR(11) NOT NULL,
    id_local INT NOT NULL,
    status VARCHAR(50) NOT NULL,
    dt_hora_inicio DATETIME NOT NULL,
    dt_hora_fim DATETIME NULL,
    CONSTRAINT fk_ronda_seguranca
        FOREIGN KEY (cpf_seguranca) REFERENCES seguranca (cpf_seguranca),
    CONSTRAINT fk_ronda_local
        FOREIGN KEY (id_local) REFERENCES local (id_local)
);

CREATE TABLE IF NOT EXISTS ocorrencia(
    id_ocorrencia INT AUTO_INCREMENT PRIMARY KEY,
    id_ronda INT NULL,
    titulo VARCHAR(50),
    data_hora DATETIME NOT NULL,
    descricao VARCHAR(500) NOT NULL,
    tipo_ocorrencia VARCHAR(50),
    URL_foto VARCHAR(255),
    CONSTRAINT fk_ocorrencia_ronda
        FOREIGN KEY (id_ronda) REFERENCES ronda (id_ronda)
);

INSERT INTO seguranca (cpf_seguranca, nome, telefone, email, codigo_identificacao, posto_principal) 
VALUES ('47589052825', 'Anthony', '11999999999', 'anthony@gmail.com', '4433', 'Bloco L');

SELECT * FROM seguranca;

use Aquacodeupx2;
SELECT * FROM seguranca;

create view vw_quantidade_vigilantes as
select count(cpf_seguranca) from seguranca;
