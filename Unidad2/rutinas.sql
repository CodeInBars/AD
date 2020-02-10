-- Función que comprueba el login de un usuario
-- devuelve 0 si el usuario y la clave son correctos
-- y -1 si no lo son
delimiter //
SET GLOBAL log_bin_trust_function_creators = 1//
drop function if exists validarUS//
create function validarUS(pUsuario varchar(10), pClave varchar(10))
	returns int   
begin
	declare vUsuario varchar(10);
	select usuario
		into vUsuario
		from usuario
		where usuario = pUsuario and
			clave = aes_encrypt(pClave,0);
	if(vUsuario is null) then
		return -1;
    else
		return 0;
    end if;
    
end//

drop procedure if exists  borrarTablas//
create procedure borrarTablas()
begin
    delete from  piezareparacion;
    delete from  pieza;
	delete from  reparacion;
    delete from  coche;
    delete from  cliente;
    delete from tiporep;
    select 'Todos los datos se han borrado';
end//
delimiter ;




