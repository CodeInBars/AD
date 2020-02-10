-- Script que añade una tabla usuario a la
-- bd taller. Lo vamos a llamar desde el programa java

drop table if exists usuario;
create table usuario(
	usuario varchar(10) primary key not null,
    clave blob not null
)engine innodb;

insert into usuario values 
('root', aes_encrypt('root',0)),
('rosa',aes_encrypt('rosa',0));



