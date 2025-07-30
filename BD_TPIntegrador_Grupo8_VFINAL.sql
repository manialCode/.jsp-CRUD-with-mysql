create database TPIntegrador_Grupo8_VFinal;

use TPIntegrador_Grupo8_VFinal;

-- ----------------------------------------------------------------------PROVINCIAS

CREATE TABLE Provincias (
    id_prov INT PRIMARY KEY AUTO_INCREMENT,
    descripcion_prov VARCHAR(100)
);

-- ----------------------------------------------------------------------LOCALIDADES

CREATE TABLE Localidades (
    id_loc INT PRIMARY KEY AUTO_INCREMENT,
    idPro_loc INT,
    descripcion_loc VARCHAR(100),
    FOREIGN KEY (idPro_loc) REFERENCES Provincias(id_prov)
);

-- ----------------------------------------------------------------------TIPODEUSUARIOS

CREATE TABLE TiposUsuarios (
    id_tu INT PRIMARY KEY AUTO_INCREMENT,
    descripcion_tu VARCHAR(50)
);

-- ----------------------------------------------------------------------USUARIOS

CREATE TABLE Usuario (
    id_us INT PRIMARY KEY AUTO_INCREMENT,
    usuario_us VARCHAR(50),
    contrasena_us VARCHAR(100),
    idTipo_us INT,
    FOREIGN KEY (idTipo_us) REFERENCES TiposUsuarios(id_tu)
);

-- ----------------------------------------------------------------------CLIENTES

CREATE TABLE Clientes (
    dni_cli VARCHAR (20) PRIMARY KEY,
    cuil_cli VARCHAR(20),
    nombre_cli VARCHAR(50),
    apellido_cli VARCHAR(50),
    sexo_cli INT,
    fecha_nacimiento_cli DATE,
    direccion_cli VARCHAR(100),
    idProvincia_cli INT,
    idLocalidad_cli INT,
    correo_cli VARCHAR(100),
    telefono_cli VARCHAR(20),
    idUsuario_cli INT,
    estado_cli BOOL, 
    FOREIGN KEY (idProvincia_cli) REFERENCES Provincias(id_prov),
    FOREIGN KEY (idLocalidad_cli) REFERENCES Localidades(id_loc),
    FOREIGN KEY (idUsuario_cli) REFERENCES Usuario(id_us)
);

-- ----------------------------------------------------------------------TIPODECUENTA

CREATE TABLE TipoCuenta (
    id_tc INT PRIMARY KEY AUTO_INCREMENT,
    descripcion_tc VARCHAR(50)
);

-- ----------------------------------------------------------------------CUENTAS

CREATE TABLE Cuentas (
    CBU_cu VARCHAR(50),
    dni_cu VARCHAR(50),
    fechaCreacion_cu DATE,
    numero_cu VARCHAR(20),
    idTipo_cu INT,
    saldo_cu DECIMAL(10,2),
    estado_cu BOOL,
    PRIMARY KEY (CBU_cu, dni_cu),
    FOREIGN KEY (dni_cu) REFERENCES Clientes(dni_cli),
    FOREIGN KEY (idTipo_cu) REFERENCES TipoCuenta(id_tc)
);

-- ----------------------------------------------------------------------TIPO DE MOVIMIENTOS

CREATE TABLE TiposMovimientos (
    id_tm INT PRIMARY KEY AUTO_INCREMENT,
    descripcion_tm VARCHAR(100)
);

-- ----------------------------------------------------------------------MOVIMIENTOS

CREATE TABLE Movimientos (
    idMovimiento_mo INT PRIMARY KEY AUTO_INCREMENT,
    CBU_mo VARCHAR(50),
    fecha_mo DATE,
    importe_mo DECIMAL(10,2),
    tipoDeMovimiento_mo INT,
    detalle_mo VARCHAR(200),
    FOREIGN KEY (CBU_mo) REFERENCES Cuentas(CBU_cu),
    FOREIGN KEY (tipoDeMovimiento_mo) REFERENCES TiposMovimientos(id_tm)
);

-- ----------------------------------------------------------------------PRESTAMOS

CREATE TABLE Prestamos (
    id_pre INT PRIMARY KEY AUTO_INCREMENT,
    CBU_pre VARCHAR(50),
    dni_pre VARCHAR(20),
    fecha_pre DATE,
    importePedido_pre DECIMAL(10,2),
    intereses_pre DECIMAL(5,2),
    cantCuotas_pre INT,
    montoTotal_pre DECIMAL(10,2),
    montoMensual_pre DECIMAL(10,2),
    estado_pre VARCHAR(20), -- 'Pendiente', 'Aprobado', 'Rechazado'
    FOREIGN KEY (CBU_pre) REFERENCES Cuentas(CBU_cu),
    FOREIGN KEY (dni_pre) REFERENCES Clientes(dni_cli)
);

-- ----------------------------------------------------------------------CUOTAS

CREATE TABLE Cuotas (
    id_cuo INT PRIMARY KEY AUTO_INCREMENT,
    idPrestamo_cuo INT,
    monto_cuo DECIMAL(10,2),
    fechaVencimiento_cuo DATE,
    fechaPago_cuo DATE NULL,
    CBU_pago_cuo VARCHAR(50),
    estado_cuo BOOL, 
    FOREIGN KEY (idPrestamo_cuo) REFERENCES Prestamos(id_pre),
    FOREIGN KEY (CBU_pago_cuo) REFERENCES Cuentas(CBU_cu)
);


-- ----------------------------------------------------------------------PROCEDIMIENTOS ALMACENADOS----------------------------------------------------------------

-- -------------------------------------------------------------------CREAR CLIENTE

DELIMITER $$

CREATE PROCEDURE crearCliente(
	usuario VARCHAR(100),
    contra VARCHAR(100),
    dni VARCHAR(100),
    nombre VARCHAR(100),
    apellido VARCHAR(100),
    sexo INT,
    cuil VARCHAR(100),
    correo VARCHAR(100),
    fecha_nacimiento DATE,
    direccion VARCHAR(100),
    idLocalidad INT,
    idProvincia INT,
    telefono VARCHAR(100)
)
BEGIN
	DECLARE idUsuario INT;
    
	INSERT INTO Usuario (
		usuario_us,
		contrasena_us,
		idTipo_us
	) 
	VALUES (
		usuario,
		contra,
		2
	);
    
	SET idUsuario = LAST_INSERT_ID();
    
	INSERT INTO Clientes (
		dni_cli,
		cuil_cli,
		nombre_cli,
		apellido_cli,
        sexo_cli,
		fecha_nacimiento_cli,
		direccion_cli,
		idProvincia_cli,
		idLocalidad_cli,
		correo_cli,
		telefono_cli,
		idUsuario_cli,
		estado_cli
	)
	VALUES (
		dni,
		cuil,
		nombre,
		apellido,
        sexo,
		fecha_nacimiento,
		direccion,
		idProvincia,
		idLocalidad,
		correo,
		telefono,
		idUsuario,
		1
	);
    
END $$

DELIMITER ;

-- -------------------------------------------------------------------CREAR CUENTA

DELIMITER $$

CREATE PROCEDURE crearCuenta(
	idTipo VARCHAR(100),
    dni VARCHAR(100),
    numero VARCHAR(100),
    CBU VARCHAR(100)
)
BEGIN
    DECLARE cuentas INT;

    SET cuentas = (SELECT COUNT(*) FROM cuentas WHERE dni_cu = dni AND estado_cu = 1);

    IF (cuentas < 3) THEN
        INSERT INTO cuentas(
            CBU_cu,
            dni_cu,
            fechaCreacion_cu,
            numero_cu,
            idTipo_cu,
            saldo_cu,
            estado_cu
        )
        VALUES(
            CBU,
            dni,
            CURDATE(),
            numero,
            idTipo,
            10000,
            1
        );
    END IF;
    
END $$

DELIMITER ;

-- -------------------------------------------------------------------ELIMINAR CLIENTE

DELIMITER $$

CREATE PROCEDURE eliminarCliente(
	dni varchar(100)
)
begin
 
 update clientes
 set estado_cli = false
 where dni_cli = dni;

END $$

DELIMITER ;

-- -------------------------------------------------------------------ELIMINAR CUENTA

DELIMITER $$

CREATE PROCEDURE eliminarCuenta(
    cbu varchar(100)
)
BEGIN
    UPDATE cuentas
    SET estado_cu = 0
    WHERE CBU_cu = cbu;

END $$

DELIMITER ;

-- -------------------------------------------------------------------MODIFICAR CLIENTE

DELIMITER $$

CREATE PROCEDURE modificarCliente(
    IN contrasena VARCHAR(100),
    IN dni VARCHAR(20),
    IN nombre VARCHAR(50),
    IN apellido VARCHAR(50),
    IN fecha_nacimiento DATE,
    IN direccion_cli VARCHAR(100),
    IN idProvincia_cli INT,
    IN idLocalidad_cli INT,
    IN correo VARCHAR(100),
    IN telefono_cli VARCHAR(20)
)
BEGIN
    DECLARE idUsuario INT;
    
    SET idUsuario = (SELECT idUsuario_cli FROM Clientes WHERE dni_cli = dni);
    
    UPDATE usuario
    SET contrasena_us = contrasena
    WHERE id_us = idUsuario;
    
    UPDATE clientes
    SET nombre_cli = nombre,
        apellido_cli = apellido,
        fecha_nacimiento_cli = fecha_nacimiento,
        direccion_cli = direccion_cli,
        idProvincia_cli = idProvincia_cli,
        idLocalidad_cli = idLocalidad_cli,
        correo_cli = correo,
        telefono_cli = telefono_cli
    WHERE dni_cli = dni;

END $$

DELIMITER ;

-- -------------------------------------------------------------------MODIFICAR CUENTA

DELIMITER $$

CREATE PROCEDURE modificarCuenta(
    IN p_IdTipoCuenta INT,
    IN p_Dni_cu VARCHAR(50),
    IN p_NumeroCuenta VARCHAR(50),
    IN p_CBU VARCHAR(50)
)
BEGIN
    UPDATE Cuentas
    SET 
        idTipo_cu = p_IdTipoCuenta,
        numero_cu = p_NumeroCuenta
    WHERE 
        CBU_cu = p_CBU
        AND dni_cu = p_Dni_cu;

END $$

DELIMITER ;

-- -------------------------------------------------------------------CREAR MOVIMIENTO

DELIMITER $$

create procedure crearMovimiento
(
in CBU varchar(50),
in fecha date,
in importe decimal(10,2),
in tipoDeMovimiento int(11),
in detalle varchar(200)
)
BEGIN

insert into movimientos(CBU_mo, fecha_mo, importe_mo, tipoDeMovimiento_mo, detalle_mo)
values(
CBU, fecha, importe, tipoDeMovimiento, detalle
);

END

DELIMITER ;

-- -------------------------------------------------------------------ELIMINAR PRESTAMO

DELIMITER $$

create procedure eliminarPrestamo
(
   id int
)
BEGIN
  UPDATE prestamos
  SET estado_pre = 0
  WHERE id_pre = id;
END $$

DELIMITER ;

-- -------------------------------------------------------------------MODIFICAR PRESTAMO

DELIMITER $$

create procedure modificarPrestamo
(
    id int,
    cbu varchar(50),
    dni varchar(50),
    fecha date,
    importe decimal(10,2),
    interes decimal(10,2),
    montoTotal decimal(10,2),
    montoMensual decimal(10,2),
    estado varchar(20)
)
BEGIN
    UPDATE prestamos
    SET CBU_pre = cbu,
    dni_pre = dni,
    fecha_pre = fecha,
    importePedido_pre = importe,
    intereses_pre = intereses,
    montoTotal_pre = montoTotal,
    montoMensual_pre = montoMensual,
    estado_pre = estado
    WHERE id_pre = id;

    END $$
    
DELIMITER ;

-- -------------------------------------------------------------------ACEPTAR PRESTAMO

DELIMITER $$

CREATE PROCEDURE aceptarPrestamo(
id int
)
BEGIN
DECLARE cantidadCuotas int;
DECLARE monto decimal(10,2);
DECLARE i INT DEFAULT 1;
DECLARE fechaVencimiento DATE;

  UPDATE prestamos SET estado_pre = 'Aprobado'
  WHERE id_pre = id;
  
  
  SET cantidadCuotas = (SELECT cantCuotas_pre FROM prestamos WHERE id_pre = id);
  SET monto = (SELECT montoMensual_pre FROM prestamos WHERE id_pre = id);


  SET i = 1;
  
  loopCuotas: LOOP
  IF i > cantidadCuotas THEN
	LEAVE loopCuotas;
  END IF;
  
	SET fechaVencimiento = DATE_ADD(CURDATE(), INTERVAL i MONTH);
  
  	INSERT INTO cuotas (monto_cuo,idPrestamo_cuo,fechaVencimiento_cuo,estado_cuo)
    VALUES (monto,id,fechaVencimiento,0);
  SET i = i+1;
  END LOOP loopCuotas;
END $$

DELIMITER ;

-- -------------------------------------------------------------------RECHAZAR PRESTAMO

DELIMITER $$

CREATE PROCEDURE rechazarPrestamo(
  id int
  )
BEGIN
  UPDATE prestamos SET estado_pre = 'Rechazado'
  WHERE id = id_pre;


END $$

DELIMITER ;

-- -------------------------------------------------------------------CREAR PRESTAMOS

DELIMITER $$

CREATE PROCEDURE crearPrestamo(
    IN p_dni_cli VARCHAR(20),
    IN p_cbu_destino VARCHAR(50),
    IN p_fecha DATE,
    IN p_importe DECIMAL(10,2),
    IN p_interes DECIMAL(5,2),
    IN p_cantCuotas INT,
    IN p_montoTotal DECIMAL(10,2),
    IN p_montoMensual DECIMAL(10,2)
)
BEGIN
    DECLARE existeCuenta INT DEFAULT 0;

    -- Validar que la cuenta exista, pertenezca al cliente y esté activa
    SELECT COUNT(*) INTO existeCuenta
    FROM Cuentas
    WHERE CBU_cu = p_cbu_destino
      AND dni_cu = p_dni_cli
      AND estado_cu = 1;

    -- Si la cuenta es válida, se registra el préstamo
    IF existeCuenta = 1 THEN
        INSERT INTO Prestamos (
            dni_pre, CBU_pre, fecha_pre,
            importePedido_pre, intereses_pre, cantCuotas_pre,
            montoTotal_pre, montoMensual_pre, estado_pre
        )
        VALUES (
            p_dni_cli, p_cbu_destino, p_fecha,
            p_importe, p_interes, p_cantCuotas,
            p_montoTotal, p_montoMensual, 'Pendiente'
        );
    END IF;
END $$

DELIMITER ;

-- -------------------------------------------------------------------ELIMINAR CUOTA


DELIMITER $$

CREATE PROCEDURE eliminarCuota (in idCuota int)
BEGIN
update cuotas
set estado = 0
where id_cuo = idCuota;
END$$

DELIMITER ;

-- -------------------------------------------------------------------MODIFICAR CUOTA

DELIMITER $$

CREATE PROCEDURE modificarCuota(
    in idCuota int,
    in idPrestamo int,
    in monto decimal(10,2), 
    in vencimiento date,
    in fechaDePago date,
    in cbu varchar(50)
)
BEGIN
    update cuotas 
    set 
        idPrestamo_cuo = idPrestamo,
        monto_cuo = monto,
        fechaVencimiento_cuo = vencimiento,
        fechaPago_cuo = fechaDePago,
        CBU_pago_cuo = cbu
    where id_cuo = idCuota;
END$$

DELIMITER ;


-- -------------------------------------------------------------------ENVIAR TRANSFERENCIA

DELIMITER $$

CREATE PROCEDURE enviarTransferencia(
    CBU varchar(50),
    importe decimal (10,2),
    detalle varchar(100),
    CBU_Destino varchar(50)
)
BEGIN

DECLARE saldo_actual DECIMAL(10,2);

    START TRANSACTION;

    -- Obtener saldo actual
    SELECT saldo_cu INTO saldo_actual
    FROM Cuentas
    WHERE CBU_cu = CBU;

    -- Verificar si hay fondos suficientes
    IF saldo_actual < importe THEN
        ROLLBACK;
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Fondos insuficientes en la cuenta de origen.';
    END IF;

INSERT INTO movimientos (CBU_mo, fecha_mo, importe_mo, tipoDeMovimiento_mo, detalle_mo)
SELECT CBU_Destino,  CURDATE(),  importe,  4, detalle;

INSERT INTO movimientos (CBU_mo, fecha_mo, importe_mo, tipoDeMovimiento_mo, detalle_mo)
SELECT CBU,  CURDATE(),  (0-importe),  4,  detalle;

-- Actualizar saldos
    UPDATE Cuentas
    SET saldo_cu = saldo_cu - importe
    WHERE CBU_cu = CBU;

    UPDATE Cuentas
    SET saldo_cu = saldo_cu + importe
    WHERE CBU_cu = CBU_Destino;

    COMMIT; 

END $$

DELIMITER ;

-- -------------------------------------------------------------------PAGAR CUOTAS

DELIMITER //
CREATE PROCEDURE pagarCuotas (
    IN listaCuotas TEXT,
    IN cbuCuenta VARCHAR(22)
)
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE cuotaId INT;
    DECLARE totalMonto DECIMAL(10,2) DEFAULT 0.0;

    -- Cursor para recorrer la lista de cuotas separadas por coma
    DECLARE cur CURSOR FOR
        SELECT CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(listaCuotas, ',', n.n), ',', -1) AS UNSIGNED) AS cuotaId
        FROM (
            SELECT 1 AS n UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION
            SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10
        ) n
        WHERE n.n <= 1 + LENGTH(listaCuotas) - LENGTH(REPLACE(listaCuotas, ',', ''));

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    OPEN cur;

    read_loop: LOOP
        FETCH cur INTO cuotaId;
        IF done THEN
            LEAVE read_loop;
        END IF;

        -- Sumar el importe de la cuota si está pendiente
        SELECT monto_cuo INTO @monto FROM cuotas WHERE id_cuo = cuotaId AND estado_cuo = 0;

        SET totalMonto = totalMonto + IFNULL(@monto, 0);

        -- Cambiar estado y fecha de pago solo si estaba pendiente
        UPDATE cuotas 
        SET estado_cuo = 1,
            fechaPago_cuo = CURDATE(),
            CBU_pago_cuo = cbuCuenta
        WHERE id_cuo = cuotaId AND estado_cuo = 0;
    END LOOP;

    CLOSE cur;

    -- Restar total del saldo de la cuenta
    UPDATE cuentas 
    SET saldo_cu = saldo_cu - totalMonto
    WHERE CBU_cu = cbuCuenta;

END //
DELIMITER ;

-- -------------------------------------------------------------------REPORTES 1

DELIMITER $$

CREATE PROCEDURE reportes1(
    fecha_inicio date,
    fecha_fin date
)
BEGIN
SELECT fecha_mo, importe_mo, tipoDeMovimiento_mo, CBU_mo FROM movimientos
WHERE
fecha_mo BETWEEN fecha_inicio AND fecha_fin;
END $$

DELIMITER ;

-- -------------------------------------------------------------------REPORTES 2

DELIMITER $$

CREATE PROCEDURE reportes2(
	dni varchar(50) ,
    apellido varchar(50),
    tipo_cuenta int
)
BEGIN
SELECT * FROM prestamos
 LEFT JOIN clientes ON dni_pre = dni_cli 
 LEFT JOIN cuentas ON CBU_cu = CBU_pre
 WHERE 
(dni_pre=dni OR dni = " ") AND 
(apellido_cli=apellido OR apellido = " ") AND
(idTipo_cu=tipo_cuenta OR tipo_cuenta = 0);

END $$

DELIMITER ;
 
 -- -------------------------------------------------------------------REPORTES 3
 
DELIMITER $$

CREATE PROCEDURE reportes3
()
BEGIN
SELECT COUNT(*) AS Cantidad, descripcion_tc AS 'Tipo de Cuenta' FROM cuentas LEFT JOIN tipocuenta ON idTipo_cu = id_tc GROUP BY idTipo_cu; 
END $$

DELIMITER ;

-- -------------------------------------------------------------------REPORTE 4


DELIMITER //

CREATE PROCEDURE reportes4
BEGIN
	SELECT
    COUNT(id_pre) AS total_prestamos, -- El conteo de préstamos para cada estado
        estado_pre                     -- La columna que indica el estado del préstamo
    FROM
        prestamos                       -- De la tabla de préstamos
    GROUP BY
        estado_pre;

END 

DELIMITER ;

-- -------------------------------------------------------------------REPORTE 5

DELIMITER //

CREATE PROCEDURE reportes5()
BEGIN
    -- Consulta para contar los movimientos agrupados por tipo de movimiento,
    -- mostrando la descripción del tipo de movimiento.
    SELECT
        tm.descripcion_tm AS 'Tipo de Movimiento',   -- Columna para la descripción del tipo de movimiento
        COUNT(m.idMovimiento_mo) AS TotalMovimientos -- Columna para el conteo total de movimientos
    FROM
        movimientos AS m                             -- Tabla de movimientos, con alias 'm'
    LEFT JOIN
        tiposmovimientos AS tm ON m.tipoDeMovimiento_mo = tm.id_tm -- Unión con la tabla de tipos de movimientos
    GROUP BY
        tm.descripcion_tm                            -- Agrupa los resultados por la descripción del tipo de movimiento
    ORDER BY
        tm.descripcion_tm;                           -- Ordena los resultados por la descripción del tipo de movimiento
END //

DELIMITER ;


-- ----------------------------------------------------------------------TRIGGERS---------------------------------------------------------------------------------

-- -------------------------------------------------------------------ACEPTAR PRESTAMOS MOVIMIENTO

DELIMITER //

CREATE TRIGGER trg_prestamo_aprobado_movimiento
AFTER UPDATE ON prestamos
FOR EACH ROW
BEGIN
    -- Check if the loan status changed from PENDIENTE to APROBADO
    IF OLD.estado_pre = 'PENDIENTE' AND NEW.estado_pre = 'APROBADO' THEN
        -- Insert a new movement record
        INSERT INTO Movimientos (
            CBU_mo,
            fecha_mo,
            importe_mo,
            tipoDeMovimiento_mo,
            detalle_mo
        ) VALUES (
            NEW.CBU_pre, -- CBU de la cuenta asociada al préstamo
            CURDATE(),   -- Fecha actual del movimiento
            NEW.importePedido_pre, -- El monto que se acredita a la cuenta (importe pedido)
            2,           -- ID del tipo de movimiento (ej. 'Solicitud de préstamo' si ID 2, o un nuevo ID para 'Préstamo Aprobado')
            CONCAT('Préstamo aprobado (ID:', NEW.id_pre, ') acreditado en cuenta.')
        );

        -- Update the account balance
        UPDATE Cuentas
        SET saldo_cu = saldo_cu + NEW.importePedido_pre
        WHERE CBU_cu = NEW.CBU_pre;
    END IF;
END//

DELIMITER ;


-- -------------------------------------------------------------------MOVIMIENTO PAGO CUOTA

DELIMITER //

CREATE TRIGGER tr_movimiento_pago_de_cuoto
AFTER UPDATE ON cuotas
FOR EACH ROW
BEGIN
	IF OLD.estado_cuo = 0 AND NEW.estado_cuo = 1 THEN
		INSERT INTO movimientos (
			CBU_mo,
            fecha_mo,
            importe_mo,
            tipoDeMovimiento_mo,
            detalle_mo
		) VALUES (
			NEW.CBU_pago_cuo,
            CURDATE(),
            NEW.monto_cuo,
            3,
            CONCAT('Cuota con el ID "', NEW.id_cuo, '" ha sido pagada. ')
		);
        END IF;
END//

DELIMITER;


-- -------------------------------------------------------------------PRESTAMO PAGADO

DELIMITER //

CREATE TRIGGER tr_actualizar_estado_prestamo_pagado
AFTER UPDATE ON cuotas
FOR EACH ROW
BEGIN
    DECLARE v_cuotas_pendientes INT;
    
    IF NEW.estado_cuo = 1 AND OLD.estado_cuo = 0 THEN
        SELECT COUNT(*)
        INTO v_cuotas_pendientes
        FROM cuotas
        WHERE idPrestamo_cuo = NEW.idPrestamo_cuo
          AND estado_cuo = 0;

        IF v_cuotas_pendientes = 0 THEN
            UPDATE prestamos
            SET estado_pre = 'Pago'
            WHERE id_pre = NEW.idPrestamo_cuo;
        END IF;
    END IF;
END //

DELIMITER ;

-- -------------------------------------------------------------------MOVIMIENTO CREACION CUENTA **HAY QUE EDITARLO PORQUE ROMPE EL PROGRAMA


DELIMITER //

CREATE TRIGGER tr_registrar_movimiento_creacion_cuenta
AFTER INSERT ON cuentas  
FOR EACH ROW
BEGIN
    
    DECLARE v_id_tipo_mov_creacion INT;
    SELECT id_tm INTO v_id_tipo_mov_creacion FROM tiposmovimientos WHERE descripcion_tm = 'Alta cuenta' LIMIT 1;

    -- Insertamos el registro en la tabla 'movimientos'
    INSERT INTO movimientos (CBU_mo, fecha_mo, importe_mo, tipoDeMovimiento_mo, detalle_mo)
    VALUES (
        NEW.CBU_cu,                   -- Accediendo a la columna 'CBU_cu' de la fila recién insertada en 'cuentas'
        NOW(),                        -- Fecha y hora actual del movimiento
        10000.00,                     -- El importe fijo de 10000
        v_id_tipo_mov_creacion,       -- ID del tipo de movimiento 'Alta cuenta'
        'Alta de cuenta'          -- Detalle del movimiento
    );
END //

DELIMITER ;



-- ----------------------------------------------------------------------DATOS---------------------------------------------------------------------------------

-- -----------------------------------------------------------------------TIPODEUSUARIOS

INSERT INTO TiposUsuarios (id_tu, descripcion_tu) VALUES
(1, 'Usuario Banco'),
(2, 'Usuario Cliente');


-- -----------------------------------------------------------------------USUARIOS

INSERT INTO Usuario (usuario_us, contrasena_us, idTipo_us) VALUES
('admin1', 'admin123', 1),
('admin2', 'admin222', 1),
('admin3', 'admin333', 1),
('admin4', 'admin444', 1),
('user5', 'pass5', 2),
('user6', 'pass6', 2),
('user7', 'pass7', 2),
('user8', 'pass8', 2),
('user9', 'pass9', 2),
('user10', 'pass10', 2),
('user11', 'pass11', 2),
('user12', 'pass12', 2),
('user13', 'pass13', 2),
('user14', 'pass14', 2),
('user15', 'pass15', 2);


-- ----------------------------------------------------------------------PROVINCIAS

INSERT INTO Provincias (descripcion_prov) VALUES
('Buenos Aires'),
('Córdoba'),
('Santa Fe'),
('Mendoza'),
('Salta');


-- ----------------------------------------------------------------------LOCALIDADES

INSERT INTO Localidades (idPro_loc, descripcion_loc) VALUES
(1, 'La Plata'),
(1, 'Mar del Plata'),
(1, 'Bahía Blanca'),
(2, 'Córdoba Capital'),
(2, 'Villa Carlos Paz'),
(2, 'Río Cuarto'),
(3, 'Rosario'),
(3, 'Santa Fe Capital'),
(3, 'Rafaela'),
(4, 'Mendoza Capital'),
(4, 'San Rafael'),
(4, 'Godoy Cruz'),
(5, 'Salta Capital'),
(5, 'Cafayate'),
(5, 'Tartagal');


-- ----------------------------------------------------------------------CLIENTES

INSERT INTO Clientes (dni_cli, cuil_cli, nombre_cli, apellido_cli, sexo_cli, fecha_nacimiento_cli, direccion_cli, idProvincia_cli, idLocalidad_cli, correo_cli, telefono_cli, idUsuario_cli, estado_cli) VALUES
('30000000', '20300000003', 'juan', 'perez', 2, '2001-07-16', 'Calle 1', 2, 13, 'juanPerez@mail.com', '1118923022', 1, 1),
('30000001', '20300000013', 'maria', 'gomez', 2, '1999-08-04', 'Calle 2', 1, 4, 'mariaGomez@mail.com', '1152042563', 2, 1),
('30000002', '20300000023', 'lucas', 'sanchez', 1, '2008-05-31', 'Calle 3', 1, 15, 'lucas.sanchez@mail.com', '1175813908', 3, 1),
('30000003', '20300000033', 'ana', 'rodriguez', 2, '2000-10-04', 'Calle 4', 1, 14, 'ana.rodriguez@mail.com', '1122717271', 4, 1),
('30000004', '20300000043', 'carlos', 'lopez', 2, '1997-03-01', 'Calle 5', 5, 5, 'carlos.lopez@mail.com', '1127163713', 5, 1),
('30000005', '20300000053', 'valeria', 'fernandez', 2, '2010-08-27', 'Calle 6', 1, 7, 'valeria.fernandez@mail.com', '1137063853', 6, 1),
('30000006', '20300000063', 'pedro', 'garcia', 1, '1995-03-14', 'Calle 7', 1, 5, 'pedro.garcia@mail.com', '1196833395', 7, 1),
('30000007', '20300000073', 'laura', 'martinez', 2, '2002-09-07', 'Calle 8', 5, 4, 'laura.martinez@mail.com', '1157369877', 8, 1),
('30000008', '20300000083', 'jose', 'diaz', 1, '2011-11-22', 'Calle 9', 3, 12, 'jose.diaz@mail.com', '1118802459', 9, 1),
('30000009', '20300000093', 'camila', 'torres', 1, '2006-05-26', 'Calle 10', 3, 13, 'camila.torres@mail.com', '1190908443', 10, 1),
('30000010', '20300000103', 'martin', 'ramirez', 2, '1999-10-04', 'Calle 11', 5, 8, 'martin.ramirez@mail.com', '1117659144', 11, 1),
('30000011', '20300000113', 'florencia', 'flores', 2, '1999-12-18', 'Calle 12', 4, 11, 'florencia.flores@mail.com', '1124784566', 12, 1),
('30000012', '20300000123', 'andres', 'morales', 1, '2004-02-25', 'Calle 13', 1, 2, 'andres.morales@mail.com', '1184335004', 13, 1),
('30000013', '20300000133', 'carla', 'suarez', 1, '2003-01-22', 'Calle 14', 1, 9, 'carla.suarez@mail.com', '1142586849', 14, 1),
('30000014', '20300000143', 'diego', 'gimenez', 2, '1998-05-14', 'Calle 15', 2, 2, 'diego.gimenez@mail.com', '1133474763', 15, 1);
    
   
-- ----------------------------------------------------------------------TIPODECUENTA
 
 
INSERT INTO TipoCuenta (descripcion_tc) VALUES
('Caja de Ahorro'),
('Cuenta Corriente');    
    

-- ----------------------------------------------------------------------CUENTAS    
    
INSERT INTO Cuentas (CBU_cu, dni_cu, fechaCreacion_cu, numero_cu, idTipo_cu, saldo_cu, estado_cu) VALUES
('50000000000000000000', '30000004', '2023-01-01', '10010', 1, 10000.00, 1),
('50000000000000000001', '30000004', '2023-01-02', '10011', 2, 10000.00, 1),
('60000000000000000000', '30000005', '2023-01-01', '10012', 1, 10000.00, 1),
('60000000000000000001', '30000005', '2023-01-02', '10013', 2, 10000.00, 1),
('70000000000000000000', '30000006', '2023-01-03', '10014', 1, 10000.00, 1),
('70000000000000000001', '30000006', '2023-01-02', '10015', 2, 10000.00, 1),
('80000000000000000000', '30000007', '2023-01-03', '10016', 1, 10000.00, 1),
('80000000000000000001', '30000007', '2023-01-04', '10017', 2, 10000.00, 1),
('90000000000000000000', '30000008', '2023-01-04', '10018', 1, 10000.00, 1),
('90000000000000000001', '30000008', '2023-01-05', '10019', 2, 10000.00, 1),
('10000000000000000000', '30000009', '2023-01-05', '10020', 1, 10000.00, 1),
('10000000000000000001', '30000009', '2023-01-06', '10021', 2, 10000.00, 1),
('11000000000000000000', '30000010', '2023-01-06', '10022', 1, 10000.00, 1),
('11000000000000000001', '30000010', '2023-01-06', '10023', 2, 10000.00, 1),
('12000000000000000000', '30000011', '2023-01-07', '10024', 1, 10000.00, 1),
('12000000000000000001', '30000011', '2023-01-07', '10025', 2, 10000.00, 1),
('13000000000000000000', '30000012', '2023-01-08', '10026', 1, 10000.00, 1),
('13000000000000000001', '30000012', '2023-01-05', '10027', 2, 10000.00, 1),
('14000000000000000000', '30000013', '2023-01-09', '10028', 1, 10000.00, 1),
('14000000000000000001', '30000013', '2023-01-09', '10029', 2, 10000.00, 1),
('15000000000000000000', '30000014', '2023-01-07', '10030', 1, 10000.00, 1),
('15000000000000000001', '30000014', '2023-01-05', '10031', 2, 10000.00, 1);   


-- ----------------------------------------------------------------------TIPO DE MOVIMIENTOS

INSERT INTO TiposMovimientos (descripcion_tm) VALUES
('Alta cuenta'),
('Alta préstamo'),
('Pago cuota'),
('Transferencia');


-- ----------------------------------------------------------------------MOVIMIENTOS

INSERT INTO Movimientos (CBU_mo, fecha_mo, importe_mo, tipoDeMovimiento_mo, detalle_mo) VALUES
('50000000000000000000', '2023-01-01', 10000.00, 1, 'Alta de cuenta'),
('50000000000000000001', '2023-01-02', 10000.00, 1, 'Alta de cuenta'),
('60000000000000000000', '2023-01-01', 10000.00, 1, 'Alta de cuenta'),
('60000000000000000001', '2023-01-02', 10000.00, 1, 'Alta de cuenta'),
('70000000000000000000', '2023-01-03', 10000.00, 1, 'Alta de cuenta'),
('70000000000000000001', '2023-01-02', 10000.00, 1, 'Alta de cuenta'),
('80000000000000000000', '2023-01-03', 10000.00, 1, 'Alta de cuenta'),
('80000000000000000001', '2023-01-04', 10000.00, 1, 'Alta de cuenta'),
('90000000000000000000', '2023-01-04', 10000.00, 1, 'Alta de cuenta'),
('90000000000000000001', '2023-01-05', 10000.00, 1, 'Alta de cuenta'),
('10000000000000000000', '2023-01-05', 10000.00, 1, 'Alta de cuenta'),
('10000000000000000001', '2023-01-06', 10000.00, 1, 'Alta de cuenta'),
('11000000000000000000', '2023-01-06', 10000.00, 1, 'Alta de cuenta'),
('11000000000000000001', '2023-01-06', 10000.00, 1, 'Alta de cuenta'),
('12000000000000000000', '2023-01-07', 10000.00, 1, 'Alta de cuenta'),
('12000000000000000001', '2023-01-07', 10000.00, 1, 'Alta de cuenta'),
('13000000000000000000', '2023-01-08', 10000.00, 1, 'Alta de cuenta'),
('13000000000000000001', '2023-01-05', 10000.00, 1, 'Alta de cuenta'),
('14000000000000000000', '2023-01-09', 10000.00, 1, 'Alta de cuenta'),
('14000000000000000001', '2023-01-09', 10000.00, 1, 'Alta de cuenta'),
('15000000000000000000', '2023-01-07', 10000.00, 1, 'Alta de cuenta'),
('15000000000000000001', '2023-01-05', 10000.00, 1, 'Alta de cuenta');
		
 
-- ----------------------------------------------------------------------PRESTAMOS
 
INSERT INTO Prestamos (CBU_pre, dni_pre, fecha_pre, importePedido_pre, intereses_pre, cantCuotas_pre, montoTotal_pre, montoMensual_pre, estado_pre) VALUES
('70000000000000000001', '30000006', '2024-06-01', 82500.00, 50, 24, 123750.00, 5156.25, 'Pendiente'),
('12000000000000000001', '30000011', '2024-06-02', 22000.00, 35, 12, 29700.00, 2475.00, 'Pendiente'),
('10000000000000000001', '30000009', '2024-06-03', 17500.00, 20, 6, 21000.00, 3500.00, 'Pendiente'),
('11000000000000000001', '30000010', '2024-06-04', 20500.00, 10, 3, 22550.00, 7516.67, 'Pendiente'),
('70000000000000000001', '30000006', '2024-06-05', 51000.00, 20, 6, 61200.00, 10200.00, 'Pendiente'),
('90000000000000000001', '30000008', '2024-06-06', 50500.00, 35, 12, 68175.00, 5681.25, 'Aprobado'),
('70000000000000000001', '30000006', '2024-06-07', 74500.00, 10, 3, 81950.00, 27316.67, 'Aprobado'),
('70000000000000000000', '30000006', '2024-06-08', 65000.00, 10, 3, 71500.00, 23833.33, 'Aprobado'),
('12000000000000000001', '30000011', '2024-06-09', 82000.00, 50, 24, 123000.00, 5125.00, 'Aprobado'),
('70000000000000000000', '30000006', '2024-06-10', 30000.00, 35, 12, 40500.00, 3375.00, 'Aprobado'),
('60000000000000000001', '30000005', '2024-06-11', 40500.00, 50, 24, 60750.00, 2531.25, 'Rechazado'),
('50000000000000000000', '30000004', '2024-06-12', 54000.00, 20, 6, 64800.00, 10800.00, 'Rechazado'),
('10000000000000000000', '30000009', '2024-06-13', 77500.00, 35, 12, 104625.00, 8718.75, 'Rechazado'),
('13000000000000000000', '30000012', '2024-06-14', 37000.00, 10, 3, 40700.00, 13566.67, 'Rechazado'),
('10000000000000000001', '30000009', '2024-06-15', 62000.00, 20, 6, 74400.00, 12400.00, 'Rechazado');


-- ----------------------------------------------------------------------CUOTAS

INSERT INTO Cuotas (idPrestamo_cuo, monto_cuo, fechaVencimiento_cuo, fechaPago_cuo, CBU_pago_cuo, estado_cuo) VALUES
(6, 5681.25, '2024-06-06', NULL, NULL, 0),
(6, 5681.25, '2024-07-06', NULL, NULL, 0),
(6, 5681.25, '2024-08-06', NULL, NULL, 0),
(6, 5681.25, '2024-09-06', NULL, NULL, 0),
(6, 5681.25, '2024-10-06', NULL, NULL, 0),
(6, 5681.25, '2024-11-06', NULL, NULL, 0),
(6, 5681.25, '2024-12-06', NULL, NULL, 0),
(6, 5681.25, '2025-01-06', NULL, NULL, 0),
(6, 5681.25, '2025-02-06', NULL, NULL, 0),
(6, 5681.25, '2025-03-06', NULL, NULL, 0),
(6, 5681.25, '2025-04-06', NULL, NULL, 0),
(6, 5681.25, '2025-05-06', NULL, NULL, 0),

(7, 27316.67, '2024-06-07', NULL, NULL, 0),
(7, 27316.67, '2024-07-07', NULL, NULL, 0),
(7, 27316.67, '2024-08-07', NULL, NULL, 0),

(8, 23833.33, '2024-06-08', NULL, NULL, 0),
(8, 23833.33, '2024-07-08', NULL, NULL, 0),
(8, 23833.33, '2024-08-08', NULL, NULL, 0),

(9, 5125.00, '2024-06-09', NULL, NULL, 0),
(9, 5125.00, '2024-07-09', NULL, NULL, 0),
(9, 5125.00, '2024-08-09', NULL, NULL, 0),
(9, 5125.00, '2024-09-09', NULL, NULL, 0),
(9, 5125.00, '2024-10-09', NULL, NULL, 0),
(9, 5125.00, '2024-11-09', NULL, NULL, 0),
(9, 5125.00, '2024-12-09', NULL, NULL, 0),
(9, 5125.00, '2025-01-09', NULL, NULL, 0),
(9, 5125.00, '2025-02-09', NULL, NULL, 0),
(9, 5125.00, '2025-03-09', NULL, NULL, 0),
(9, 5125.00, '2025-04-09', NULL, NULL, 0),
(9, 5125.00, '2025-05-09', NULL, NULL, 0),
(9, 5125.00, '2025-06-09', NULL, NULL, 0),
(9, 5125.00, '2025-07-09', NULL, NULL, 0),
(9, 5125.00, '2025-08-09', NULL, NULL, 0),
(9, 5125.00, '2025-09-09', NULL, NULL, 0),
(9, 5125.00, '2025-10-09', NULL, NULL, 0),
(9, 5125.00, '2025-11-09', NULL, NULL, 0),
(9, 5125.00, '2025-12-09', NULL, NULL, 0),
(9, 5125.00, '2026-01-09', NULL, NULL, 0),
(9, 5125.00, '2026-02-09', NULL, NULL, 0),
(9, 5125.00, '2026-03-09', NULL, NULL, 0),
(9, 5125.00, '2026-04-09', NULL, NULL, 0),
(9, 5125.00, '2026-05-09', NULL, NULL, 0),

(10, 3375.00, '2024-06-10', NULL, NULL, 0),
(10, 3375.00, '2024-07-10', NULL, NULL, 0),
(10, 3375.00, '2024-08-10', NULL, NULL, 0),
(10, 3375.00, '2024-09-10', NULL, NULL, 0),
(10, 3375.00, '2024-10-10', NULL, NULL, 0),
(10, 3375.00, '2024-11-10', NULL, NULL, 0),
(10, 3375.00, '2024-12-10', NULL, NULL, 0),
(10, 3375.00, '2025-01-10', NULL, NULL, 0),
(10, 3375.00, '2025-02-10', NULL, NULL, 0),
(10, 3375.00, '2025-03-10', NULL, NULL, 0),
(10, 3375.00, '2025-04-10', NULL, NULL, 0),
(10, 3375.00, '2025-05-10', NULL, NULL, 0);
