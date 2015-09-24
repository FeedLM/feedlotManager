-- 2015-09-23 
create table genealogia(
	id_animal	char(36)	not null,
	id_madre	char(36)	not null,
	id_padre	char(36)	not null,
	primary key(id_animal, id_madre, id_padre));

-- 2015-09-23	
USE `feedlotmanager`;

DELIMITER $$

DROP TRIGGER IF EXISTS feedlotmanager.cria_AUPD$$
USE `feedlotmanager`$$

CREATE
DEFINER=`root`@`localhost`
TRIGGER `feedlotmanager`.`cria_AUPD`
AFTER UPDATE ON `feedlotmanager`.`cria`
FOR EACH ROW
BEGIN	

	-- se se esta actualizando el id_animal crear genealogia
	-- if new.id_animal <> old.id_animal then

		-- buscar el id del padre en semental de la madre
		insert into genealogia( 
				id_animal, 		id_madre, 		id_padre)
		select	cria.id_animal,	cria.id_madre,	id_semental
		from 	cria, animal
		where	cria.id_cria		=	NEW.id_cria
		and		animal.id_animal	=	cria.id_madre;
	-- end if;

	-- FTP
	DELETE FROM repl_cria
	WHERE	id_rancho	=	NEW.id_rancho
	AND		id_madre	=	NEW.id_madre
	AND		id_cria		=	NEW.id_cria;

	INSERT INTO repl_cria
	SELECT	NEW.id_rancho,
			NEW.id_madre,
			NEW.id_cria,
			NOW(), 
			'PE';
	-- FTP
END$$
DELIMITER ;
	
