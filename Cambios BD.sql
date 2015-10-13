-- 2015-09-23 
create table genealogia(
	id_animal	char(36)	not null,
	id_madre	char(36)	not null,
	id_padre	char(36)	not null,
	primary key(id_animal, id_madre, id_padre));

-- 2015-09-23	
USE `feedlotmanager`;

DELIMITER $$

DROP TRIGGER IF EXISTS cria_AUPD$$
USE `feedlotmanager`$$

CREATE
DEFINER=`root`@`localhost`
TRIGGER `cria_AUPD`
AFTER UPDATE ON `cria`
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

--2015-09-25
ALTER TABLE `feedlotmanager`.`animal` ADD COLUMN `es_vientre` CHAR(1) NULL  AFTER `id_raza` ;

--2015-09-25
USE `feedlotmanager`;
DROP procedure IF EXISTS `actualizarAnimal`;

DELIMITER $$

USE `feedlotmanager`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizarAnimal`(
    varIdAnimal		CHAR(36),		varIdProveedor		CHAR(36),		
	varFechaCompra	DATETIME,		varCompra			CHAR(255),
	varNumeroLote	CHAR(255),		varPesoCompra		DECIMAL(20,4),	
	varIdSexo		CHAR(36),		varFechaIngreso		DATETIME,
	varAreteVisual	CHAR(255),		varAreteElectronico	CHAR(255),
	varAreteSiniiga	CHAR(255),		varAreteCampa�a		CHAR(255),
	varPesoActual	DECIMAL(20,4),	varTemperatura		DECIMAL(20,4),
	varEsSemental	CHAR(1),		varIdSemental		CHAR(36),
	varIdRaza		CHAR(36),		varStatus			CHAR(1),
	varEsVientre	CHAR(1))
BEGIN
	
	UPDATE animal set 
		id_proveedor		=	varIdProveedor,			fecha_compra	=	varFechaCompra,
		compra				=	varCompra,				numero_lote		=	varNumeroLote,
		peso_compra			=	varPesoCompra,			id_sexo			=	varIdSexo,
		fecha_ingreso		=	varFechaIngreso,		arete_visual	=	varAreteVisual,
		arete_electronico	=	varAreteElectronico,	arete_siniiga	=	varAreteSiniiga,
		arete_campa�a		=	varAreteCampa�a,		peso_actual		=	varPesoActual,
		temperatura			=	varTemperatura,			es_semental		=	varEsSemental,
		id_semental			=	varIdSemental,			id_raza			=	varIdRaza,
		status				=	varStatus,				es_vientre		=	varEsVientre		
	WHERE	id_animal	=	varIdAnimal;	

END$$

DELIMITER ;

--2015-09-25
USE `feedlotmanager`;

DROP procedure IF EXISTS `agregarAnimal`;

DELIMITER $$

USE `feedlotmanager`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `agregarAnimal`(
    varIdRancho		CHAR(36),		varIdCorral     	CHAR(36),		varIdProveedor	CHAR(36),		
	varFechaCompra	DATETIME,		varCompra			CHAR(255),		varNumeroLote	CHAR(255),
	varPesoCompra	DECIMAL(20,4),	varIdSexo			CHAR(36),		varFechaIngreso	DATETIME,
	varAreteVisual	CHAR(255),		varAreteElectronico	CHAR(255),		varAreteSiniiga	CHAR(255),
	varAreteCampa�a	CHAR(255),		varPesoActual		DECIMAL(20,4),	varTemperatura	DECIMAL(20,4),
	varEsSemental	CHAR(1),		varIdSemental		CHAR(36),		varIdRaza		CHAR(36),
	varStatus		CHAR(1),		varIdCria			CHAR(36),		varEsVientre	CHAR(1))
BEGIN
    DECLARE varIdAnimal CHAR(36);

	SELECT UUID()
	INTO varIdAnimal;

	INSERT corral_animal
    (   id_rancho,    id_corral,    id_animal)
    SELECT
        varIdRancho, varIdCorral, varIdAnimal;

    INSERT animal
    (	id_animal,		id_proveedor,		fecha_compra,	compra,
		numero_lote,	peso_compra,		id_sexo,		fecha_ingreso,
		arete_visual,	arete_electronico,	arete_siniiga,	arete_campa�a,
		peso_actual,	temperatura,		es_semental,	id_semental,
		id_raza,		status,				es_vientre)
    SELECT
		varIdAnimal,	varIdProveedor,			varFechaCompra,		varCompra,
		varNumeroLote,	varPesoCompra,			varIdSexo,			varFechaIngreso,
		varAreteVisual,	varAreteElectronico,	varAreteSiniiga,	varAreteCampa�a,
		varPesoActual,	varTemperatura,			varEsSemental,		varIdSemental,		
		varIdRaza,		varStatus,				varEsVientre;

    call movimientoPeso( varIdRancho, varIdAnimal, varFechaIngreso, varPesoActual);

	update	cria 
	set		id_animal	=	varIdAnimal
	where	id_cria		=	varIdCria;
END$$

DELIMITER ;

--2015-09-25
USE `feedlotmanager`;
DROP procedure IF EXISTS `agregarCria`;

DELIMITER $$
USE `feedlotmanager`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `agregarCria`(
    varIdRancho	CHAR(36),		varIdMadre			CHAR(36),            
    varArete	CHAR(255),		varFechaNacimiento	DATETIME,    
    varIdSexo	CHAR(36),		varIdRaza			CHAR(36),	
	varPeso		DECIMAL(20,4),	varIdTipoParto		CHAR(36))
BEGIN
    DECLARE varIdCria, 
			varIdCorral CHAR(36);

	SELECT	UUID()
    INTO	varIdCria;    
    
    INSERT cria
    (	id_rancho,		id_madre,			id_cria,
		arete,			fecha_nacimiento,	id_sexo,
		id_raza,		status,				peso,
		id_tipo_parto)
    SELECT
		varIdRancho,	varIdMadre,			varIdCria,        
		varArete,		varFechaNacimiento,	varIdSexo,    
		varIdRaza,		'A',				varPeso,
		varIdTipoParto;

	-- obtener Corral de la madre
	SELECT	id_corral
	INTO	varIdCorral
	FROM	corral_animal
	WHERE	id_rancho	=	varIdRancho
	AND		id_animal	=	varIdMadre;

	-- Agregar kardex de la Cria
	CALL agregarAnimal(
		varIdRancho,	varIdCorral,	NULL,
		NULL,			NULL,			NULL,
		NULL,			varIdSexo,		NOW(),
		varArete,		NULL,			NULL,
		NULL,			varPeso,		NULL,
		NULL,			NULL,			varIdRaza,
		'A',			varIdCria,		'N');

END$$

DELIMITER ;

--2015-09-26
ALTER TABLE `feedlotmanager`.`movimiento` 
CHANGE COLUMN `id_cliente` `id_cliente` CHAR(36) NULL DEFAULT NULL ;

--2015-09-26
USE `feedlotmanager`;
DROP procedure IF EXISTS `movimientoSalidaGrupo`;

DELIMITER $$
USE `feedlotmanager`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `movimientoSalidaGrupo`(	
	varIdRancho 			CHAR(36),	varFechaSalida	DATETIME,
	varIdClaseMovimiento	CHAR(255),	varNumeroPedido	CHAR(255),
	varIdCliente			CHAR(255),		varPesoActual	DECIMAL(20,4),
	varIdUsuario			CHAR(36))
BEGIN
	
	DECLARE varIdAnimal,		varIdCorralOrigen,
			varIdDetalle,		varConceptoMovimiento,	
            varIdMovimiento		CHAR(36);

	DECLARE varPesoProrrateado	DECIMAL(20,4);

	DECLARE vb_termina BOOL DEFAULT FALSE;
	
	DECLARE cur_animales CURSOR
	FOR	SELECT	animal_grupo.id_animal,	id_corral                                
		FROM	animal_grupo, corral_animal
		WHERE	animal_grupo.id_animal	=	corral_animal.id_animal
        AND		animal_grupo.id_rancho	=	varIdRancho
		AND		id_usuario				=	varIdUsuario		
		AND		tipo					=	'salida';

	DECLARE CONTINUE HANDLER 
	FOR SQLSTATE '02000'
	SET vb_termina = TRUE;	

	SELECT (varPesoActual	/	(	SELECT COUNT(*)
									FROM	animal_grupo
									WHERE	id_rancho 	=	varIdRancho
									AND		id_usuario	=	varIdUsuario		
									AND		tipo		=	'salida') )
	INTO	varPesoProrrateado;

	SELECT	con_salida
	INTO	varConceptoMovimiento
	FROM	rancho
	WHERE 	id_rancho	=	varIdRancho;

	OPEN cur_animales;

	Recorre_Cursor: LOOP
		
		FETCH cur_animales INTO varIdAnimal, varIdCorralOrigen;

		IF vb_termina THEN
        
            LEAVE Recorre_Cursor;
        END IF;

		SELECT UUID()
        INTO varIdMovimiento;    

		CALL insertarMovimiento(varIdRancho,		varIdMovimiento,	varConceptoMovimiento,	
								varFechaSalida,		NULL,				varIdCorralOrigen,	
                                NULL,				NULL,				varIdClaseMovimiento,
                                varNumeroPedido,	varIdCliente,		NULL,	
                                NULL,				NULL,				NULL,	
                                NULL,				varPesoProrrateado);

		CALL insertarDetalleMovimiento(	varIdRancho,	varIdMovimiento,	varConceptoMovimiento,	varIdAnimal);

	-- Se inserta porque el movimiento genera un cambio de corral
		IF NOT EXISTS (SELECT * FROM corral_animal WHERE id_rancho = varIdRancho AND id_corral = varIdCorralOrigen AND id_animal = varIdAnimal)
		THEN
       
			INSERT INTO corral_animal
			SELECT varIdRancho, varIdCorralOrigen, varIdAnimal;
		END IF;

		UPDATE animal
		SET		status		=	'V',
				peso_actual	=	varPesoProrrateado
		WHERE 	id_animal	=	varIdAnimal;

	END LOOP;
  	CLOSE cur_animales;

	DELETE	FROM	animal_grupo
			WHERE	id_rancho 	=	varIdRancho
			AND		id_usuario	=	varIdUsuario
			AND		tipo		=	'salida';

END$$

DELIMITER ;
--
USE `feedlotmanager`;
DROP procedure IF EXISTS `reinicia_bd`;

DELIMITER $$
USE `feedlotmanager`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `reinicia_bd`()
BEGIN
/*	DECLARE varIdRancho			,
			varIdCorralAnimal	CHAR(36);
	*/
	TRUNCATE TABLE animal;
	TRUNCATE TABLE animal_grupo;
    TRUNCATE TABLE cliente;
    TRUNCATE TABLE corral;
    TRUNCATE TABLE corral_animal;
    TRUNCATE TABLE corral_datos;
    TRUNCATE TABLE compra;
    TRUNCATE TABLE concepto_movimiento;
    TRUNCATE TABLE cria;
    TRUNCATE TABLE detalle_compra;
    TRUNCATE TABLE detalle_movimiento;
    TRUNCATE TABLE medicina;
    TRUNCATE TABLE medicina_animal;
    TRUNCATE TABLE medicina_tratamiento;
    TRUNCATE TABLE movimiento;
    TRUNCATE TABLE movimiento_animal; 
    TRUNCATE TABLE proveedor;
    TRUNCATE TABLE rancho;
    TRUNCATE TABLE rancho_medicina;
    TRUNCATE TABLE tratamiento;
    TRUNCATE TABLE usuario;
                
    TRUNCATE TABLE repl_concepto_movimiento;
    TRUNCATE TABLE repl_animal;
    TRUNCATE TABLE repl_cliente;
    TRUNCATE TABLE repl_concepto_movimiento;
    TRUNCATE TABLE repl_corral;    
    TRUNCATE TABLE repl_corral_animal;
    TRUNCATE TABLE repl_corral_datos;
    TRUNCATE TABLE repl_cria;
    TRUNCATE TABLE repl_detalle_movimiento;
    TRUNCATE TABLE repl_medicina;
    TRUNCATE TABLE repl_medicina_animal;
    TRUNCATE TABLE repl_medicina_tratamiento;
    TRUNCATE TABLE repl_movimiento;
    TRUNCATE TABLE repl_movimiento_animal;
    TRUNCATE TABLE repl_proveedor;
    TRUNCATE TABLE repl_rancho;
    TRUNCATE TABLE repl_tratamiento;
    
    INSERT INTO `usuario` (`id_usuario`, `log`, `password`) VALUES ('1', 'admin', 'admin');
    /*
    SELECT 	id_rancho
    INTO	varIdRancho 
    FROM	rancho;
    
    CALL agregarCorral(varIdRancho, 'Hospital', '', '', '', '', 0.00);
    
    UPDATE corral
    SET status = 'E'
    WHERE	nombre = 'Hospital';
    
    SELECT	id_corral
    INTO 	varIdCorralAnimal
    FROM	corral
    WHERE	nombre = 'Hospital';
    
    UPDATE rancho
    SET		id_corral_hospital	=	varIdCorralAnimal;


    UPDATE rancho SET 
		con_muerte				=	(	SELECT	id_concepto	FROM	concepto_movimiento	WHERE	descripcion	=	'Muerte' 			),
		con_pesaje				=	(	SELECT	id_concepto	FROM	concepto_movimiento	WHERE	descripcion	=	'Peso'				),
		con_traspaso_salida		=	(	SELECT	id_concepto	FROM	concepto_movimiento	WHERE	descripcion	=	'Traspaso Salida'	),
		con_traspaso_entrada	=	(	SELECT	id_concepto	FROM	concepto_movimiento	WHERE	descripcion	=	'Traspaso Entrada'	),
		con_salida				=	(	SELECT	id_concepto	FROM	concepto_movimiento	WHERE	descripcion	=	'Salida'			); 

*/
    
END$$

DELIMITER ;


--2015-09-28
USE `feedlotmanager`;
DROP procedure IF EXISTS `cierreCorral`;

DELIMITER $$
USE `feedlotmanager`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `cierreCorral`(
varIdCorral CHAR(36), varIdRancho CHAR(36))
BEGIN
UPDATE animal a 
SET 
    status = 'C'
WHERE
    (SELECT 
            ca.id_animal
        FROM
            corral_animal ca
        WHERE
            ca.id_corral = varIdCorral
                AND a.id_animal = ca.id_animal
                AND a.status = 'A') = a.id_animal;

UPDATE corral c 
SET 
    status = 'C',
    fecha_cierre = NOW()
WHERE
    c.id_corral = varIdCorral
        AND c.id_rancho = varIdRancho;
END$$

DELIMITER ;


-- 2015-10-08 18:33
USE `feedlotmanager`;
DROP procedure IF EXISTS `actualizarAnimal`;

DELIMITER $$
USE `feedlotmanager`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizarAnimal`(
    varIdAnimal		CHAR(36),		varIdProveedor		CHAR(36),		
	varFechaCompra	DATETIME,		varCompra			CHAR(255),
	varNumeroLote	CHAR(255),		varPesoCompra		DECIMAL(20,4),	
	varIdSexo		CHAR(36),		varFechaIngreso		DATETIME,
	varAreteVisual	CHAR(255),		varAreteElectronico	CHAR(255),
	varAreteSiniiga	CHAR(255),		varAreteCampaña		CHAR(255),
	varPesoActual	DECIMAL(20,4),	varTemperatura		DECIMAL(20,4),
	varEsSemental	CHAR(1),		varIdSemental		CHAR(36),
	varIdRaza		CHAR(36),		varStatus			CHAR(1),
	varEsVientre	CHAR(1))
BEGIN
	
	UPDATE animal set 
		id_proveedor		=	varIdProveedor,			fecha_compra	=	varFechaCompra,
		compra				=	varCompra,				numero_lote		=	varNumeroLote,
		peso_compra			=	varPesoCompra,			id_sexo			=	varIdSexo,
		fecha_ingreso		=	varFechaIngreso,		arete_visual	=	varAreteVisual,
		arete_electronico	=	varAreteElectronico,	arete_siniiga	=	varAreteSiniiga,
		arete_campaña		=	varAreteCampaña,		peso_actual		=	varPesoActual,
		temperatura			=	varTemperatura,			es_semental		=	varEsSemental,
		id_semental			=	varIdSemental,			id_raza			=	varIdRaza,
		status				=	varStatus,				es_vientre		=	varEsVientre		
	WHERE	id_animal	=	varIdAnimal;	

END$$

DELIMITER ;

-- 2015-10-09 13:38

USE `feedlotmanager`;
DROP procedure IF EXISTS `reactivarCorral`;

DELIMITER $$
USE `feedlotmanager`$$
CREATE PROCEDURE `reactivarCorral` (varIdCorral CHAR(36), varIdRancho CHAR(36))
BEGIN
UPDATE animal a 
SET 
    status = 'A'
WHERE
    (SELECT 
            ca.id_animal
        FROM
            corral_animal ca
        WHERE
            ca.id_corral = varIdCorral
                AND a.id_animal = ca.id_animal
                AND a.status = 'C') = a.id_animal;

UPDATE corral c 
SET 
    status = 'S'
WHERE
    c.id_corral = varIdCorral
        AND c.id_rancho = varIdRancho;
END
$$

DELIMITER ;

-- 2015-10-09 17:47

ALTER TABLE `feedlotmanager`.`corral` 
ADD COLUMN `fecha_cierre` DATETIME NULL DEFAULT NULL AFTER `total_costo_medicina`;

-- 2015-10-10 14:15

USE `feedlotmanager`;
DROP procedure IF EXISTS `agregarAnimal`;

DELIMITER $$
USE `feedlotmanager`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `agregarAnimal`(
    varIdRancho		CHAR(36),		varIdCorral     	CHAR(36),		varIdProveedor	CHAR(36),		
	varFechaCompra	DATETIME,		varCompra			CHAR(255),		varNumeroLote	CHAR(255),
	varPesoCompra	DECIMAL(20,4),	varIdSexo			CHAR(36),		varFechaIngreso	DATETIME,
	varAreteVisual	CHAR(255),		varAreteElectronico	CHAR(255),		varAreteSiniiga	CHAR(255),
	varAreteCampaña	CHAR(255),		varPesoActual		DECIMAL(20,4),	varTemperatura	DECIMAL(20,4),
	varEsSemental	CHAR(1),		varIdSemental		CHAR(36),		varIdRaza		CHAR(36),
	varStatus		CHAR(1),		varIdCria			CHAR(36),		varEsVientre	CHAR(1))
BEGIN
    DECLARE varIdAnimal CHAR(36);

	SELECT UUID()
	INTO varIdAnimal;

	INSERT corral_animal
    (   id_rancho,    id_corral,    id_animal)
    SELECT
        varIdRancho, varIdCorral, varIdAnimal;

    INSERT animal
    (	id_animal,		id_proveedor,		fecha_compra,	compra,
		numero_lote,	peso_compra,		id_sexo,		fecha_ingreso,
		arete_visual,	arete_electronico,	arete_siniiga,	arete_campaña,
		peso_actual,	temperatura,		es_semental,	id_semental,
		id_raza,		status,				es_vientre)
    SELECT
		varIdAnimal,	varIdProveedor,			varFechaCompra,		varCompra,
		varNumeroLote,	varPesoCompra,			varIdSexo,			varFechaIngreso,
		varAreteVisual,	varAreteElectronico,	varAreteSiniiga,	varAreteCampaña,
		varPesoActual,	varTemperatura,			varEsSemental,		varIdSemental,		
		varIdRaza,		varStatus,				varEsVientre;

    call movimientoPeso( varIdRancho, varIdAnimal, varFechaIngreso, varPesoActual);

	update	cria 
	set		id_animal	=	varIdAnimal
	where	id_cria		=	varIdCria;
END$$

DELIMITER ;

-- 2015-10-10 14:15

CREATE TABLE `recepcion` (

  `id_recepcion` CHAR(36) NOT NULL,  
  `id_proveedor` CHAR(36) NULL,  
  `id_origen` CHAR(36) NULL,  
  `folio` VARCHAR(45) NULL, 
  `fecha` datetime NULL,   
  `animales` INT(10) NULL,  
  `peso_origen` DECIMAL(20,4) NULL,  
  `limite_merma` DECIMAL(20,4) NULL,  
  `peso_recepcion` DECIMAL(20,4) NULL,  
  `id_lote` CHAR(36) NULL,  
  `costo_flete` DECIMAL(20,4) NULL,  
  `devoluciones` INT(10) NULL,  
  `causa_devolucion` VARCHAR(45) NULL,  
PRIMARY KEY (`id_recepcion`));

CREATE TABLE `lote` (  
  `id_lote` CHAR(36) NOT NULL,  
  `descripcion_lote` VARCHAR(45) NULL,  
  `merma` DECIMAL(20,4) NULL,  
  `porcentaje_merma` DECIMAL(20,4) NULL,  
  `total_alimento` DECIMAL(20,4) NULL,  
PRIMARY KEY (`id_lote`));

USE `feedlotmanager`;
DROP procedure IF EXISTS `agregarLote`;

DELIMITER $$
USE `feedlotmanager`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `agregarLote`(
    varDescripcionLote	CHAR(45))
BEGIN
    DECLARE varIdLote CHAR(36);

	SELECT UUID()
	INTO varIdLote;

	INSERT lote
    (   id_lote,	descripcion_lote)
    SELECT
        varIdLote, 	varDescripcionLote;
END$$

DELIMITER ;

USE `feedlotmanager`;
DROP procedure IF EXISTS `agregarRecepcion`;

DELIMITER $$
USE `feedlotmanager`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `agregarRecepcion`(
    varIdProveedor	CHAR(36),		varIdOrigen			CHAR(36),		varFolio			CHAR(45),
    varFecha		DATETIME,		varAnimales			int(10),		varPesoOrigen		DECIMAL(20,4),
	varLimiteMerma	DECIMAL(20,4),	varPesoRecepcion	DECIMAL(20,4),	varDescripcionLote	char(45),
	varCostoFlete	DECIMAL(20,4),	varDevoluciones		int(10),		varCausaDevolucion 	char(45))
BEGIN
    DECLARE varIdRecepcion ,
			varIdLote	char(36);
	
	SELECT UUID()
	INTO varIdRecepcion;
	
    call agregarLote(varDescripcionLote);
    
    select id_lote
    into	varIdLote
    from 	lote
    where 	descripcion_lote	=	varDescripcionLote;
    	
    INSERT recepcion
    (	id_recepcion,	id_proveedor,	id_origen,		folio,
		fecha, 			animales, 		peso_origen,	limite_merma,
		peso_recepcion,	id_lote,		costo_flete,	devoluciones,
        causa_devolucion)
    SELECT
		varIdRecepcion,		varIdProveedor,		varIdOrigen,	varFolio,
		varFecha,			varAnimales,		varPesoOrigen,	varLimiteMerma,
		varPesoRecepcion,	varIdLote,			varCostoFlete,	varDevoluciones,
		varCausaDevolucion;	
END$$

DELIMITER ;


