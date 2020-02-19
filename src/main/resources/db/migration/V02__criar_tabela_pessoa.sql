CREATE TABLE pessoa (
   codigo SERIAL PRIMARY KEY,
   nome VARCHAR(50) NOT NULL,
   ativo boolean not null,
   logradouro varchar(100),
   numero varchar(20),
   complemento varchar(100),
   bairro varchar(100),
   cep varchar (12),
   cidade varchar(150),
   estado varchar(20)
);

INSERT INTO pessoa (codigo, nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) values (1,'Daiane', TRUE, 'r. rio grande', '546', 'apt-12', 'jd. independencia', '03.223-110', 'SLP', 'sp');
INSERT INTO pessoa (codigo, nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) values (2,'Vitor', TRUE, 'r. rio verde', '100', 'casa', 'jd. nossa senhora', '43.623-110', 'Rio preto', 'sp');
INSERT INTO pessoa (codigo, nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) values (3,'Vera', TRUE, 'r. rio grande do sul', '546', 'casa', 'jd. vila nova', '15.600-110', 'Fernandopolis', 'sp');
