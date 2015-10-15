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
    (   id_rancho,		id_corral,		id_animal)
    SELECT
        varIdRancho,	varIdCorral,	varIdAnimal;

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
  `id_recepcion` 		CHAR(36) 		NOT NULL,  
  `id_proveedor` 		CHAR(36) 			NULL,  
  `id_origen` 			CHAR(36) 			NULL,  
  `folio` 				VARCHAR(45) 		NULL,
  `fecha_compra` 		datetime 			NULL,	
  `fecha_recepcion` 	datetime 			NULL,   
  `animales` 			INT(10) 			NULL,  
  `peso_origen` 		DECIMAL(20,4) 		NULL,  
  `limite_merma` 		DECIMAL(20,4) 		NULL,  
  `merma`				DECIMAL(20,4) 		NULL,  
  `porcentaje_merma` 	DECIMAL(20,4) 		NULL, 
  `peso_recepcion` 		DECIMAL(20,4) 		NULL,  
  `numero_lote` 		CHAR(255) 			NULL,  
  `costo_flete` 		DECIMAL(20,4) 		NULL,  
  `devoluciones` 		INT(10) 			NULL,  
  `causa_devolucion` 	VARCHAR(45) 		NULL, 
  `total_alimento` 		DECIMAL(20,4) 		NULL,   
PRIMARY KEY (`id_recepcion`));

USE `feedlotmanager`;
DROP procedure IF EXISTS `agregarRecepcion`;

DELIMITER $$
USE `feedlotmanager`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `agregarRecepcion`(
    varIdProveedor		CHAR(36),		varIdOrigen			CHAR(36),		varFolio			CHAR(45),
    varFechaCompra		DATETIME,		varFechaRecepcion	DATETIME,		varAnimales			int(10),
	varPesoOrigen		DECIMAL(20,4),	varLimiteMerma		DECIMAL(20,4),	varMerma			decimal(20,5),
	varPorcentajeMerma	decimal(20,4),	varPesoRecepcion	DECIMAL(20,4),	varNumeroLote		char(255),	
	varCostoFlete		DECIMAL(20,4),	varDevoluciones		int(10),		varCausaDevolucion	varchar(45))
BEGIN
    DECLARE varIdRecepcion char(36);
	
	SELECT	UUID()
	INTO	varIdRecepcion;
     	
    INSERT recepcion
    (	id_recepcion,	id_proveedor,		id_origen,			folio,
		fecha_compra,	fecha_recepcion,	animales,			peso_origen,	
		limite_merma,	merma,				porcentaje_merma,	peso_recepcion,
		numero_lote,	costo_flete,		devoluciones,		causa_devolucion	)
    SELECT
		varIdRecepcion,	varIdProveedor,		varIdOrigen,		varFolio,
		varFechaCompra,	varFechaRecepcion,	varAnimales,		varPesoOrigen,		
		varLimiteMerma,	varMerma,			varPorcentajeMerma,	varPesoRecepcion,
		varNumeroLote,	varCostoFlete,		varDevoluciones,	varCausaDevolucion;	
END$$

DELIMITER ;

ALTER TABLE `feedlotmanager`.`animal` 
ADD COLUMN `porcentaje_merma` 			DECIMAL(20,4) NULL AFTER `es_vientre`,
ADD COLUMN `costo_flete` 				DECIMAL(20,4) NULL AFTER `porcentaje_merma`,
ADD COLUMN `total_alimento` 			DECIMAL(20,4) NULL AFTER `costo_flete`,
ADD COLUMN `costo_alimento` 			DECIMAL(20,4) NULL AFTER `total_alimento`,
ADD COLUMN `promedio_alimento` 			DECIMAL(20,4) NULL AFTER `costo_alimento`,
ADD COLUMN `promedio_costo_alimento`	DECIMAL(20,4) NULL AFTER `promedio_alimento`,
ADD COLUMN `fecha_ultima_comida`		DECIMAL(20,4) NULL AFTER `promedio_costo_alimento`;


CREATE TABLE `feedlotmanager`.`ingreso_alimento` (
  `id_ingreso_alimento`	CHAR(36) NOT NULL,
  `numero_lote` 		VARCHAR(45) NULL,
  `id_corral` CHAR(36) NULL,
  `total_alimento` DECIMAL(20,4) NULL,
  `fecha` DATETIME NULL,
  `costo_unitario` DECIMAL(20,4) NULL,
  `costo_total` DECIMAL(20,4) NULL,
  `carro` VARCHAR(45) NULL,
PRIMARY KEY (`id_ingreso_alimento`));
  
USE `feedlotmanager`;
DROP procedure IF EXISTS `agregarIngresoAlimento`;

DELIMITER $$
USE `feedlotmanager`$$
CREATE DEFINER = `root`@`localhost` PROCEDURE `agregarIngresoAlimento`(
    varNumeroLote	CHAR(255),		varIdCorral			CHAR(36),		varTotalAlimento	decimal(20,4),
    varFecha		DATETIME,		varCostoUnitario	DECIMAL(20,4),	varCostoTotal		DECIMAL(20,4),	
    varCarro		varchar(45))
BEGIN
    DECLARE varIdIngresoAlimento char(36);
	
	SELECT	UUID()
	INTO	varIdIngresoAlimento;
     	
    INSERT ingreso_alimento
    (	id_ingreso_alimento,	numero_lote,	id_corral,		total_alimento,
		fecha,					costo_unitario,	costo_total, 	carro	)
    SELECT
		varIdIngresoAlimento,	varNumeroLote,		varIdCorral,	varTotalAlimento,
        varFecha,				varCostoUnitario,	varCostoTotal,	varCarro;
END$$

DELIMITER ;

USE `feedlotmanager`;

DELIMITER $$

DROP TRIGGER IF EXISTS feedlotmanager.ingreso_alimento_AFTER_INSERT$$
USE `feedlotmanager`$$
CREATE DEFINER = CURRENT_USER TRIGGER `ingreso_alimento_AFTER_INSERT`
AFTER INSERT ON `ingreso_alimento` 
FOR EACH ROW
begin
    declare varAlimentoAnimal decimal(20,4);
          
     
    if new.id_corral = "" then 
		
        select 	new.total_alimento / count(*)
        into	varAlimentoAnimal 
        from   	animal
        where  	numero_lote = new.numero_lote
        and		coalesce( id_corral, '' ) = '';
        
        update	animal
        set		total_alimento		=	total_alimento	+	varAlimentoAnimal,
				costo_alimento		=	costo_alimento	+	(	varAlimentoAnimal	*	new.costo_unitario	),
                fecha_ultima_comida	=	NOW()
        where	numero_lote		=	new.numero_lote
        and		coalesce( id_corral, '') = '';
    end if;    
    
    if new.numero_lote  = "" then 
		
        select 	new.total_alimento / count(*)
        into	varAlimentoAnimal 
        from   	animal
        where  	coalesce( numero_lote, '')  = ''
        and		id_corral					= new.id_corral;
        
        update	animal
        set		total_alimento 		=	total_alimento + varAlimentoAnimal,
				costo_alimento 		=	costo_alimento + (  varAlimentoAnimal  * new.costo_unitario ),
                fecha_ultima_comida	=	NOW()
        where	coalesce( numero_lote, '')  = ''
        and		id_corral					= new.id_corral;
    end if;    
end$$
DELIMITER ;

USE `feedlotmanager`;

DELIMITER $$

DROP TRIGGER IF EXISTS feedlotmanager.animal_AUPD$$
USE `feedlotmanager`$$
CREATE DEFINER=`root`@`localhost` TRIGGER `animal_AUPD`
AFTER UPDATE ON `animal`
FOR EACH ROW
BEGIN	

	DECLARE varIdCorral ,
			varIdRancho	CHAR(36);
	declare varFechaRecepcion datetime;

	SELECT	id_corral, 		id_rancho
	INTO	varIdCorral,	varIdRancho
	FROM	corral_animal
	WHERE	id_animal	=	NEW.id_animal;
	
	CALL animalesPorCorral(varIdCorral);
	/*
	IF NEW.id_semental <> NULL THEN
	
		call agregarRegistroEmpadre(NOW(),	NEW.id_animal, NEW.id_semental);
	END IF;
*/
	select	fecha_recepcion
    into	varFechaRecepcion
    from	recepcion r,	animal a
    where   r.numero_lote	=	a.numero_lote;

	update	animal
    set		promedio_alimento 		=	total_alimento	/	datediff(	fecha_ultima_comida,	varFechaRecepcion	),
			promedio_costo_alimento	=	costo_alimento	/	datediff(	fecha_ultima_comida,	varFechaRecepcion	)
	WHERE	id_animal	=	NEW.id_animal;

 -- Envio a FTP
	DELETE FROM repl_animal
	WHERE	id_rancho	=	varIdRancho
	AND		id_animal	=	NEW.id_animal;

    INSERT INTO repl_animal
	SELECT varIdRancho, NEW.id_animal, NOW(), 'PE';
 -- Envio a FTP

END$$
DELIMITER ;

ALTER TABLE `feedlotmanager`.`animal` 
ADD COLUMN `ganancia_promedio` DECIMAL(20,4) NULL AFTER `fecha_ultima_comida`;

USE `feedlotmanager`;

DELIMITER $$

DROP TRIGGER IF EXISTS feedlotmanager.animal_AUPD$$
USE `feedlotmanager`$$
CREATE DEFINER=`root`@`localhost` TRIGGER `animal_AUPD`
AFTER UPDATE ON `animal`
FOR EACH ROW
BEGIN	

	DECLARE varIdCorral ,
			varIdRancho	CHAR(36);
	declare varFechaRecepcion datetime;
    declare	varGananciaPromedio decimal(20,4);
	/*
	IF NEW.id_semental <> NULL THEN
	
		call agregarRegistroEmpadre(NOW(),	NEW.id_animal, NEW.id_semental);
	END IF;
*/
	select	fecha_recepcion
    into	varFechaRecepcion
    from	recepcion r,	animal a
    where   r.numero_lote	=	a.numero_lote;

	update	animal
    set 	promedio_alimento		=	total_alimento	/	datediff(fecha_ultima_comida, varFechaRecepcion),
			promedio_costo_alimento	=	costo_alimento	/	datediff(fecha_ultima_comida, varFechaRecepcion)
	WHERE	id_animal	=	NEW.id_animal;

-- GAnancia promedio

	SELECT	ROUND(COALESCE((MAX(peso) - MIN(peso)) / DATEDIFF(MAX(fecha), MIN(fecha)),0.00),2)
    into	varGananciaPromedio
	FROM	movimiento m, detalle_movimiento d, rancho r
    WHERE	m.id_rancho	=   r.id_rancho
    AND		m.id_concepto	=   r.con_pesaje
    AND		(		m.id_rancho     =   d.id_rancho
             AND	m.id_concepto   =   d.id_concepto
             AND	m.id_movimiento =   d.id_movimiento
			 AND	d.id_animal     =   new.id_animal );

	update	animal
	set		ganancia_promedio	=	varGananciaPromedio
    WHERE	id_animal	=	NEW.id_animal;
    
	SELECT	id_corral, 		id_rancho
	INTO	varIdCorral,	varIdRancho
	FROM	corral_animal
	WHERE	id_animal	=	NEW.id_animal;
	
	CALL animalesPorCorral(varIdCorral);
	
 -- Envio a FTP
	DELETE FROM repl_animal
	WHERE	id_rancho	=	varIdRancho
	AND		id_animal	=	NEW.id_animal;

    INSERT INTO repl_animal
	SELECT varIdRancho, NEW.id_animal, NOW(), 'PE';
 -- Envio a FTP

END$$
DELIMITER ;

ALTER TABLE `feedlotmanager`.`corral` 
ADD COLUMN `ganancia_promedio` DECIMAL(20,4) NULL DEFAULT NULL AFTER `fecha_cierre`;

USE `feedlotmanager`;
DROP procedure IF EXISTS `animalesPorCorral`;

DELIMITER $$
USE `feedlotmanager`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `animalesPorCorral`(
	varIdCorral CHAR(36)	)
BEGIN
	
	UPDATE corral
	SET num_animales =	(	SELECT	count(	*	)
							FROM	animal, corral_animal
							WHERE	STATUS					=	'A'
							AND		corral_animal.id_corral	=	corral.id_corral
							and 	corral_animal.id_animal	=	animal.id_animal),
		
		total_kilos	=	(	SELECT	SUM(	peso_actual	) 
							FROM	animal, corral_animal
							WHERE	STATUS					=	'A'
							AND		corral_animal.id_corral	=	corral.id_corral
							and 	corral_animal.id_animal	=	animal.id_animal),
		
		peso_minimo	=	(	SELECT	MIN(	peso_actual	)
							FROM	animal, corral_animal
							WHERE	STATUS					=	'A'
							AND		corral_animal.id_corral	=	corral.id_corral
							and 	corral_animal.id_animal	=	animal.id_animal),
		
		peso_maximo	=	(	SELECT	MAX(	peso_actual	)
							FROM	animal, corral_animal
							WHERE	STATUS					=	'A'
							AND		corral_animal.id_corral	=	corral.id_corral
							and 	corral_animal.id_animal	=	animal.id_animal),
		
		peso_promedio	=	(	SELECT	AVG(	peso_actual	)
								FROM	animal, corral_animal
								WHERE	STATUS					=	'A'
								AND		corral_animal.id_corral	=	corral.id_corral
								and 	corral_animal.id_animal	=	animal.id_animal),

		peso_ganancia	=	(	SELECT	SUM(	peso_actual	) - SUM(	peso_compra) 
								FROM	animal, corral_animal
								WHERE	STATUS					=	'A'
								AND		corral_animal.id_corral	=	corral.id_corral
								and 	corral_animal.id_animal	=	animal.id_animal),

		id_raza			=	(	SELECT	CASE	WHEN a1.count = 1
												THEN id_raza
												ELSE a1.mixto
										END
								FROM	(	SELECT	id_raza,	COUNT(DISTINCT id_raza) AS count, 
											(	SELECT	id_raza	
												FROM	raza
												WHERE	descripcion = 'Mixto' ) mixto
											FROM	animal a, corral_animal ca
											WHERE	a.id_animal	=	ca.id_animal
											AND  	id_corral	=	varIdCorral		)	a1	),                                                                                      
		id_sexo			=	(	SELECT	CASE	WHEN a1.count = 1
												THEN id_sexo
												ELSE a1.mixto
										END
								FROM	(	SELECT	id_sexo,	COUNT(DISTINCT id_sexo) AS count, 
											(	SELECT	id_sexo	
												FROM	sexo
												WHERE	descripcion = 'Mixto' ) mixto
												FROM	animal a, corral_animal ca
												WHERE	a.id_animal = ca.id_animal
												AND		id_corral 	= varIdCorral	)	a1	),
		
        total_kilos_iniciales	=	(	SELECT	SUM(peso_compra)										
										FROM    corral_animal c, animal a
										WHERE   c.id_animal	=	a.id_animal
										AND     c.id_corral	=	corral.id_corral),
         
		total_costo_medicina	=	(	SELECT  SUM( ma.dosis * rm.costo_promedio)
										FROM 	corral_animal ca, medicina_animal ma, medicina m, rancho_medicina rm
										WHERE 	ma.id_medicina	=	m.id_medicina
                                        AND 	rm.id_medicina 	= 	m.id_medicina
                                        AND		ca.id_animal	=	ma.id_animal
										AND		ma.id_rancho	=	ca.id_rancho
										AND 	rm.id_rancho	= 	corral.id_rancho
                                        AND		ca.id_rancho	=	corral.id_rancho
										AND		ca.id_corral	=	corral.id_corral	),
                                        
		ganancia_promedio	=	(	SELECT	AVG(	animal.ganancia_promedio		)
									FROM	animal, corral_animal
									WHERE	STATUS					=	'A'
									AND		corral_animal.id_corral	=	corral.id_corral
									and 	corral_animal.id_animal	=	animal.id_animal),
		
        promedio_alimento	=	(	SELECT	AVG(	animal.promedio_alimento		)
									FROM	animal, corral_animal
									WHERE	STATUS					=	'A'
									AND		corral_animal.id_corral	=	corral.id_corral
									and 	corral_animal.id_animal	=	animal.id_animal	)
									
	WHERE corral.id_corral	=	varIdCorral;    
    
    UPDATE	corral
    SET		peso_ganancia		=	CASE WHEN peso_ganancia < 0 THEN 0 ELSE peso_ganancia END
    WHERE	corral.id_corral	=	varIdCorral;
END$$

DELIMITER ;

USE `feedlotmanager`;

DELIMITER $$

DROP TRIGGER IF EXISTS feedlotmanager.animal_AINS$$
USE `feedlotmanager`$$
CREATE DEFINER=`root`@`localhost` TRIGGER `animal_AINS` AFTER INSERT ON `animal` FOR EACH ROW BEGIN

	DECLARE varIdCorral ,
			varIdRancho	CHAR(36);
    declare	varPorcentajeMerma decimal(20,4);
    declare	varCostoFlete		decimal(20,4);
    declare	varIdProveedor		char(36);
    declare	varFechaCompra		datetime;
    declare	varCompra			char(255);
            
	select 	porcentaje_merma, 	round(costo_flete / animales,2),
			id_proveedor,		fecha_compra,
            folio
	into	varPorcentajeMerma,	varCostoFlete,
			varIdProveedor,		varFechaCompra,
            varCompra
	from 	recepcion
    where 	recepcion.numero_lote = new.numero_lote;

	UPDATE	animal
    SET		porcentaje_merma	=	varPorcentajeMerma,
			costo_flete			=	varCostoFlete,
			id_proveedor		=	varIdProveedor,
			fecha_compra		=	varFechaCompra,
			compra				=	varCompra    
    WHERE	id_animal			=	NEW.id_animal;

	SELECT	id_corral, 		id_rancho
	INTO	varIdCorral,	varIdRancho
	FROM 	corral_animal
	WHERE	id_animal	=	NEW.id_animal;

	CALL animalesPorCorral(varIdCorral);
    
 -- Envio a FTP
	DELETE FROM repl_animal
	WHERE	id_rancho	=	varIdRancho
	AND		id_animal	=	NEW.id_animal;

    INSERT INTO repl_animal
	SELECT varIdRancho, NEW.id_animal, NOW(), 'PE';
 -- Envio a FTP

END$$
DELIMITER ;

ALTER TABLE `feedlotmanager`.`corral` 
ADD COLUMN `promedio_alimento` DECIMAL(20,4) NULL AFTER `ganancia_promedio`;

-- 2015-10-14 11:14
DELIMITER $$

DROP TRIGGER IF EXISTS ingreso_alimento_AFTER_INSERT$$

CREATE DEFINER=`root`@`localhost` TRIGGER `ingreso_alimento_AFTER_INSERT`
AFTER INSERT ON `ingreso_alimento` 
FOR EACH ROW
begin

    declare varAlimentoAnimal decimal(20,4);
              
    if new.id_corral = "" then 
		
        select 	new.total_alimento / count(*)
        into	varAlimentoAnimal 
        from   	animal
        where  	numero_lote = new.numero_lote;
        -- and		coalesce( id_corral, '' ) = '';
        
        update	animal
        set		total_alimento		=	total_alimento	+	varAlimentoAnimal,
				costo_alimento		=	costo_alimento	+	(	varAlimentoAnimal	*	new.costo_unitario	),
                fecha_ultima_comida	=	NOW()
        where	numero_lote		=	new.numero_lote;
      --   and		coalesce( id_corral, '') = '';
    end if;    
    
    if new.numero_lote  = "" then 
		
        select 	new.total_alimento / count(*)
        into	varAlimentoAnimal 
        from   	animal, corral_animal
        where  	corral_animal.id_corral		=	new.id_corral
        and     corral_animal.id_animal		=	animal.id_animal;
        
        update	animal
        set		total_alimento 		=	total_alimento	+	varAlimentoAnimal,
				costo_alimento 		=	costo_alimento	+	(  varAlimentoAnimal * new.costo_unitario ),
                fecha_ultima_comida	=	NOW()
		-- from 	corral_animal
        where	id_animal in (	select id_animal
								from   corral_animal 
								where  corral_animal.id_corral = new.id_corral);
    end if;    
end$$
DELIMITER ;

--2015 -10-14 12:23
ALTER TABLE `animal` 
CHANGE COLUMN `fecha_ultima_comida` `fecha_ultima_comida` DATETIME NULL DEFAULT NULL ;

--2015-10-14 12:33
CREATE DEFINER=`root`@`localhost` TRIGGER `feedlotmanager`.`animal_BUPD`
BEFORE UPDATE ON `feedlotmanager`.`animal`
FOR EACH ROW
BEGIN	

	declare varFechaRecepcion datetime;
    declare	varGananciaPromedio decimal(20,4);
	
	select	fecha_recepcion
    into	varFechaRecepcion
    from	recepcion r,	animal a
    where   r.numero_lote	=	a.numero_lote;

	set 	new.promedio_alimento		=	new.total_alimento	/	datediff(new.fecha_ultima_comida, varFechaRecepcion),
			new.promedio_costo_alimento	=	new.costo_alimento	/	datediff(new.fecha_ultima_comida, varFechaRecepcion);

-- GAnancia promedio

	SELECT	ROUND(COALESCE((MAX(peso) - MIN(peso)) / DATEDIFF(MAX(fecha), MIN(fecha)),0.00),2)
    into	varGananciaPromedio
	FROM	movimiento m, detalle_movimiento d, rancho r
    WHERE	m.id_rancho	=   r.id_rancho
    AND		m.id_concepto	=   r.con_pesaje
    AND		(		m.id_rancho     =   d.id_rancho
             AND	m.id_concepto   =   d.id_concepto
             AND	m.id_movimiento =   d.id_movimiento
			 AND	d.id_animal     =   new.id_animal );

	set		new.ganancia_promedio	=	varGananciaPromedio;    

END

--2015-10-14 12:33
CREATE DEFINER=`root`@`localhost` TRIGGER `feedlotmanager`.`animal_AUPD`
AFTER UPDATE ON `feedlotmanager`.`animal`
FOR EACH ROW
BEGIN	

	DECLARE varIdCorral ,
			varIdRancho	CHAR(36);            
    
	SELECT	id_corral, 		id_rancho
	INTO	varIdCorral,	varIdRancho
	FROM	corral_animal
	WHERE	id_animal	=	NEW.id_animal;
	
	CALL animalesPorCorral(varIdCorral);
	
 -- Envio a FTP
	DELETE FROM repl_animal
	WHERE	id_rancho	=	varIdRancho
	AND		id_animal	=	NEW.id_animal;

    INSERT INTO repl_animal
	SELECT varIdRancho, NEW.id_animal, NOW(), 'PE';
 -- Envio a FTP

END

-- 2015-10-14 12:34

CREATE DEFINER=`root`@`localhost` TRIGGER `ingreso_alimento_AFTER_INSERT`
AFTER INSERT ON `ingreso_alimento` 
FOR EACH ROW
begin

    declare varAlimentoAnimal decimal(20,4);
              
    if new.id_corral = "" then 
		
        select 	new.total_alimento / count(*)
        into	varAlimentoAnimal 
        from   	animal
        where  	numero_lote = new.numero_lote;
        -- and		coalesce( id_corral, '' ) = '';
        
        update	animal
        set		total_alimento		=	coalesce(total_alimento, 0.0)	+	varAlimentoAnimal,
				costo_alimento		=	coalesce(costo_alimento, 0.0)	+	(	varAlimentoAnimal	*	new.costo_unitario	),
                fecha_ultima_comida	=	NOW()
        where	numero_lote		=	new.numero_lote;
      --   and		coalesce( id_corral, '') = '';
    end if;    
    
    if new.numero_lote  = "" then 
		
        select 	new.total_alimento / count(*)
        into	varAlimentoAnimal 
        from   	animal, corral_animal
        where  	corral_animal.id_corral		=	new.id_corral
        and     corral_animal.id_animal		=	animal.id_animal;
        
        update	animal
        set		total_alimento 		=	coalesce(total_alimento, 0.0)	+	varAlimentoAnimal,
				costo_alimento 		=	coalesce(costo_alimento, 0.0)	+	(  varAlimentoAnimal * new.costo_unitario ),
                fecha_ultima_comida	=	NOW()
		-- from 	corral_animal
        where	id_animal in (	select id_animal
								from   corral_animal 
								where  corral_animal.id_corral = new.id_corral);
    end if;    
end
--2015-10-15 10:17
USE `feedlotmanager`;
DROP procedure IF EXISTS `agregarRecepcion`;

DELIMITER $$
USE `feedlotmanager`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `agregarRecepcion`(
    varIdProveedor		CHAR(36),		varIdOrigen			CHAR(36),		varFolio			CHAR(45),
    varFechaCompra		DATETIME,		varFechaRecepcion	DATETIME,		varAnimales			int(10),
	varPesoOrigen		DECIMAL(20,4),	varLimiteMerma		DECIMAL(20,4),	varMerma			decimal(20,5),
	varPorcentajeMerma	decimal(20,4),	varPesoRecepcion	DECIMAL(20,4),	varNumeroLote		char(255),	
	varCostoFlete		DECIMAL(20,4),	varDevoluciones		int(10),		varCausaDevolucion	varchar(45))
BEGIN
    DECLARE varIdRecepcion char(36);
	
	SELECT	UUID()
	INTO	varIdRecepcion;
    
 	set varMerma = varPesoOrigen - varPesoRecepcion;
    
    set varPorcentajeMerma = ( varMerma * 100 ) / varPesoOrigen;
	
	INSERT recepcion
    (	id_recepcion,	id_proveedor,		id_origen,			folio,
		fecha_compra,	fecha_recepcion,	animales,			peso_origen,	
		limite_merma,	merma,				porcentaje_merma,	peso_recepcion,
		numero_lote,	costo_flete,		devoluciones,		causa_devolucion	)
    SELECT
		varIdRecepcion,	varIdProveedor,		varIdOrigen,		varFolio,
		varFechaCompra,	varFechaRecepcion,	varAnimales,		varPesoOrigen,		
		varLimiteMerma,	varMerma,			varPorcentajeMerma,	varPesoRecepcion,
		varNumeroLote,	varCostoFlete,		varDevoluciones,	varCausaDevolucion;	
END$$

DELIMITER ;


