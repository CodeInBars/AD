drop database if exists acb;
create database acb;
use acb;

create table equipo(
	nombre varchar(50) primary key,
    localidad varchar(50)
)engine innodb;
create table jugador(
	codigo int auto_increment not null primary key,
    equipo varchar(50)  not null,
    dorsal int not null,
    nombre varchar(100) not null,
    tipo enum('P', 'B', 'A', 'E') not null,
    foreign key (equipo) references equipo(nombre) on update cascade on delete restrict
)engine innodb;

create table partido(
	codigo int auto_increment primary key  not null,
    local varchar(50)  not null,
    visitante varchar(50)  not null,
    foreign key (local) references equipo(nombre) on update cascade on delete restrict,
    foreign key (visitante) references equipo(nombre) on update cascade on delete restrict
)engine innodb;

create table tipoAccion(
	tipo varchar(1) primary key not null,
    descrip varchar(50) not null
)engine innodb;
create table accion(
	codigo int auto_increment primary key not null,
    partido int not null,
    tipo varchar(1) not null,
    jugador int not null,
    anulada boolean not null default false,
    foreign key (partido) references partido(codigo) on update cascade on delete restrict,
    foreign key (tipo) references tipoAccion(tipo) on update cascade on delete restrict,
    foreign key (jugador) references jugador(codigo) on update cascade on delete restrict
)engine innodb;

delimiter //
create procedure obtenerEstadistica(pPartido int)
begin
	declare vUnoL, vUnoV, vDosL, vDosV, vTresL, vTresV int;
	select count(a.codigo)
		into vUnoL
		from accion a join partido p
			on a.partido = p.codigo
            join jugador j
				on j.codigo = a.jugador
        where a.tipo = 1 and
			a.partido = pPartido and
            j.equipo = p.local and
            a.anulada = false;
		
	select count(a.codigo)
		into vUnoV
		from accion a join partido p
			on a.partido = p.codigo
            join jugador j
				on j.codigo = a.jugador
        where a.tipo = 1 and
			a.partido = pPartido and
            j.equipo = p.visitante and
            a.anulada = false;

	select count(a.codigo)
		into vDosL
		from accion a join partido p
			on a.partido = p.codigo
            join jugador j
				on j.codigo = a.jugador
        where a.tipo = 2 and
			a.partido = pPartido and
            j.equipo = p.local and
            a.anulada = false;
		
	select count(a.codigo)
		into vDosV
		from accion a join partido p
			on a.partido = p.codigo
            join jugador j
				on j.codigo = a.jugador
        where a.tipo = 2 and
			a.partido = pPartido and
            j.equipo = p.visitante and
            a.anulada = false;

	select count(a.codigo)
		into vTresL
		from accion a join partido p
			on a.partido = p.codigo
            join jugador j
				on j.codigo = a.jugador
        where a.tipo = 3 and
			a.partido = pPartido and
            j.equipo = p.local and
            a.anulada = false;
		
	select count(a.codigo)
		into vTresV
		from accion a join partido p
			on a.partido = p.codigo
            join jugador j
				on j.codigo = a.jugador
        where a.tipo = 3 and
			a.partido = pPartido and
            j.equipo = p.visitante and
            a.anulada = false;
	-- Salida
    select (vUnoL+vDosL*2+vTresL*3) as PuntosLocal, 
           (vUnoV+vDosV*2+vTresV*3)  as PuntosVisitante, 
           vUnoL  as Canastas1Local, 
           vUnoV   as Canastas1Visitante, 
           vDosL  as Canastas2Local, 
           vDosV  as Canastas2Visitante, 
           vTresL  as Canastas3Local, 
           vTresV  as Canastas3Visitante;
end//
create function registrarAccion(pPartido int, pTipo int, pJugador int)
	returns int
    begin
		declare vPartido, vTipo, vJugador int;
        declare vLocal, vVisitante varchar(50);
        -- Comprobaciones
        select codigo, local, visitante 
			into vPartido, vLocal, vVisitante
            from partido
            where codigo = pPartido;
        if(vPartido is null) then
			return -1;
		end if;
        select tipo 
			into vTipo
            from tipoaccion
            where tipo = pTipo;
        if(vTipo is null) then
			return -2;
		end if;
        select codigo 
			into vJugador
            from jugador
            where codigo = pJugador and
            (equipo = vLocal or equipo = vVisitante);
        if(vJugador is null) then
			return -3;
		end if;
        
        insert into accion values (null,pPartido,pTipo,pJugador,0);
        return last_insert_id();
    end//

delimiter ;

insert into tipoAccion values 
	('1','Canasta 1 punto'),
	('2','Canasta 2 puntos'),
    ('3','Canasta 3 puntos'),
    ('4','Rebote'),
    ('5','Personal'),
    ('6','Pasos'),
    ('7','Doble');

insert into equipo values
	('EM Navalmoral', 'Navalmoral'),
    ('Plasencia Basket', 'Plasencia'),
    ('Cáceres CB', 'Cáceres'),
    ('CB Hervás', 'Hervás');
    
insert into jugador values
	(null,'EM Navalmoral','1','Jugador EMN 1','B'),
    (null,'EM Navalmoral','2','Jugador EMN 2','P'),
    (null,'EM Navalmoral','3','Jugador EMN 3','A'),
    (null,'EM Navalmoral','4','Jugador EMN 4','A'),
    (null,'EM Navalmoral','5','Jugador EMN 5','P'),
    (null,'EM Navalmoral','6','Jugador EMN 6','E'),
    (null,'EM Navalmoral','7','Jugador EMN 7','P'),
    (null,'EM Navalmoral','8','Jugador EMN 8','B'),
    (null,'Plasencia Basket','1','Jugador PB 1','B'),
    (null,'Plasencia Basket','2','Jugador PB 2','B'),
    (null,'Plasencia Basket','3','Jugador PB 3','A'),
    (null,'Plasencia Basket','4','Jugador PB 4','A'),
    (null,'Plasencia Basket','5','Jugador PB 5','P'),
    (null,'Plasencia Basket','6','Jugador PB 6','P'),
    (null,'Plasencia Basket','7','Jugador PB 7','E'),
    (null,'Plasencia Basket','8','Jugador PB 8','E'),
    (null,'Cáceres CB','1','Jugador CCB 1','B'),
    (null,'Cáceres CB','2','Jugador CCB 2','P'),
    (null,'Cáceres CB','3','Jugador CCB 3','A'),
    (null,'Cáceres CB','4','Jugador CCB 4','A'),
    (null,'Cáceres CB','5','Jugador CCB 5','P'),
    (null,'Cáceres CB','6','Jugador CCB 6','E'),
    (null,'Cáceres CB','7','Jugador CCB 7','P'),
    (null,'Cáceres CB','8','Jugador CCB 8','B'),
    (null,'CB Hervás','1','Jugador HCB 1','B'),
    (null,'CB Hervás','2','Jugador HCB 2','P'),
    (null,'CB Hervás','3','Jugador HCB 3','A'),
    (null,'CB Hervás','4','Jugador HCB 4','A'),
    (null,'CB Hervás','5','Jugador HCB 5','P'),
    (null,'CB Hervás','6','Jugador HCB 6','E'),
    (null,'CB Hervás','7','Jugador HCB 7','P'),
    (null,'CB Hervás','8','Jugador HCB 8','B');
    
insert into partido select null, e1.nombre, e2.nombre from equipo e1, equipo e2 
	where e1.nombre != e2.nombre;    
