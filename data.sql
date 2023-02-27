INSERT INTO cnf_tipo_documento (`cnf_tipo_documento_id`,`abreviatura`,`nombre`,`codigo_sunat`,`flag_estado`,`name`,`value`) VALUES (2,'DNI','DOCUMENTO NACIONAL DE IDENTIDAD','1',NULL,'',NULL);
INSERT INTO cnf_tipo_documento (`cnf_tipo_documento_id`,`abreviatura`,`nombre`,`codigo_sunat`,`flag_estado`,`name`,`value`) VALUES (3,'RUC','REGISTRO ÚNICO DE CONTRIBUYENTE','6',NULL,'',NULL);

INSERT INTO cnf_tipo_comprobante (`cnf_tipo_comprobante_id`,`nombre`,`codigo_sunat`,`codigo`,`flag_electronico`) VALUES (1,'BOLETA','03','BOL','1');
INSERT INTO cnf_tipo_comprobante (`cnf_tipo_comprobante_id`,`nombre`,`codigo_sunat`,`codigo`,`flag_electronico`) VALUES (2,'FACTURA','01','FAC','1');
INSERT INTO cnf_tipo_comprobante (`cnf_tipo_comprobante_id`,`nombre`,`codigo_sunat`,`codigo`,`flag_electronico`) VALUES (4,'NOTA DE VENTA','00','NOT','0');
INSERT INTO cnf_tipo_comprobante (`cnf_tipo_comprobante_id`,`nombre`,`codigo_sunat`,`codigo`,`flag_electronico`) VALUES (5,'NOTA DE SALIDA','','NSA','0');
INSERT INTO cnf_tipo_comprobante (`cnf_tipo_comprobante_id`,`nombre`,`codigo_sunat`,`codigo`,`flag_electronico`) VALUES (6,'NOTA DE ENTRADA','','NEA','0');

INSERT INTO seg_rol (`seg_rol_id`,`descripcion`,`nombre`) VALUES (1,NULL,'ROLE_ADMIN');
INSERT INTO seg_rol (`seg_rol_id`,`descripcion`,`nombre`) VALUES (2,NULL,'ROLE_USER');

INSERT INTO cnf_moneda (cnf_moneda_id, nombre, simbolo, descripcion_plural,codigo) 
values (1, 'SOL','S/', 'SOLES','PEN');

INSERT INTO cnf_empresa (`cnf_empresa_id`,`cnf_tipo_documento_id`,`nombre`,`descripcion`,`nro_documento`,`direccion`,`telefono`,`empresacol`,
`cnf_distrito_id`,`estado`,`token`,`ruta_pse`,cnf_moneda_id) 
VALUES (1,3,'DESARROLLO INTEGRAL DE SOFTWARE','DEINSOFT','20534999616','JR LOS CLAVELES 123','1322323',NULL,1004,NULL,NULL,NULL,1);
INSERT INTO cnf_empresa (`cnf_empresa_id`,`cnf_tipo_documento_id`,`nombre`,`descripcion`,`nro_documento`,`direccion`,`telefono`,`empresacol`,
`cnf_distrito_id`,`estado`,`token`,`ruta_pse`, cnf_moneda_id) 
VALUES (2,3,'BATERIAS DON TELE','BATERIAS DON TELE','12345678901','AV PANAMERICANA SUR 1332 - A','',NULL,1004,'1',
'Bearer eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE2NTA5MjAwMTYsImlzcyI6IkRFSU5TT0ZUIiwianRpIjoiREVGQUNULUpXVCIsInN1YiI6IjEwNDE0MzE2NTk1L1BFUkVaIERFTEdBRE8gQkxBTkNBIE5FUkkiLCJudW1Eb2MiOiIxMDQxNDMxNjU5NSIsInJhem9uU29jaWFsIjoiUEVSRVogREVMR0FETyBCTEFOQ0EgTkVSSSIsInVzdWF','http://localhost:8080/api/v1/document/send-document',1);

INSERT INTO seg_usuario (`seg_usuario_id`,`nombre`,`email`,`password`,`estado`,`cnf_empresa_id`) 
VALUES (1,'admin','edward21.sistemas@gmail.com','$2a$10$ySSHW/94asbWwyduiUu6t./z761Sqgo0kJA9Q0/DexTBv9wY267wu',1,1);
INSERT INTO seg_usuario (`seg_usuario_id`,`nombre`,`email`,`password`,`estado`,`cnf_empresa_id`) 
VALUES (3,'antonio','facturacionelectronica@opendeinsoft.com','$2a$10$ySSHW/94asbWwyduiUu6t./z761Sqgo0kJA9Q0/DexTBv9wY267wu',1,1);
INSERT INTO seg_usuario (`seg_usuario_id`,`nombre`,`email`,`password`,`estado`,`cnf_empresa_id`) 
VALUES (4,'diana','dmra2001@gmail.com','$2a$10$PXjaRqL5mK1fp4kwGdtcO.i53gTzEbRnGOJUHCt4vKlVefdpks0HC',1,2);
INSERT INTO seg_usuario (`seg_usuario_id`,`nombre`,`email`,`password`,`estado`,`cnf_empresa_id`) 
VALUES (7,'diana2','edward21.sistemas2@gmail.com','$2a$10$1o1fhysFm2i/ZLZwH.mGA.g8XK4u3qT54kNomb9mPZ2/DmOo7VaZC',1,2);

INSERT INTO seg_accion (seg_accion_id,nombre) values (1,'VER OPCIÓN');

INSERT INTO `cnf_local` (`nombre`, `cnf_empresa_id`) VALUES ('LOCAL PRINCIPAL', '1');
INSERT INTO `cnf_local` (`nombre`, `cnf_empresa_id`) VALUES ('LOCAL PRINCIPAL', '2');

INSERT INTO seg_rol_usuario (`seg_rol_usuario_id`,`seg_rol_id`,`seg_usuario_id`,`cnf_empresa_id`,`cnf_local_id`) VALUES (1,1,1,1,NULL);
INSERT INTO seg_rol_usuario (`seg_rol_usuario_id`,`seg_rol_id`,`seg_usuario_id`,`cnf_empresa_id`,`cnf_local_id`) VALUES (2,2,3,1,NULL);
INSERT INTO seg_rol_usuario (`seg_rol_usuario_id`,`seg_rol_id`,`seg_usuario_id`,`cnf_empresa_id`,`cnf_local_id`) VALUES (3,1,4,2,NULL);
INSERT INTO seg_rol_usuario (`seg_rol_usuario_id`,`seg_rol_id`,`seg_usuario_id`,`cnf_empresa_id`,`cnf_local_id`) VALUES (6,2,7,2,2);


INSERT INTO seg_menu (`seg_menu_id`,`parent_id`,`nombre`,`seqorder`,`icon`,`path`) VALUES (1,NULL,'ADMINISTRACION',1,'fa-user-shield',NULL);
INSERT INTO seg_menu (`seg_menu_id`,`parent_id`,`nombre`,`seqorder`,`icon`,`path`) VALUES (2,NULL,'SEGURIDAD',1,'fa-shield-alt',NULL);
INSERT INTO seg_menu (`seg_menu_id`,`parent_id`,`nombre`,`seqorder`,`icon`,`path`) VALUES (3,NULL,'CONFIGURACION',3,'fa-cog',NULL);
INSERT INTO seg_menu (`seg_menu_id`,`parent_id`,`nombre`,`seqorder`,`icon`,`path`) VALUES (4,NULL,'INVENTARIO',4,'fa-box',NULL);
INSERT INTO seg_menu (`seg_menu_id`,`parent_id`,`nombre`,`seqorder`,`icon`,`path`) VALUES (5,NULL,'VENTAS',5,'fa-cart-plus',NULL);
INSERT INTO seg_menu (`seg_menu_id`,`parent_id`,`nombre`,`seqorder`,`icon`,`path`) VALUES (6,NULL,'CAJA',6,'fa-money-bill',NULL);
INSERT INTO seg_menu (`seg_menu_id`,`parent_id`,`nombre`,`seqorder`,`icon`,`path`) VALUES (7,NULL,'REPORTES',7,'fa-file',NULL);
INSERT INTO seg_menu (`seg_menu_id`,`parent_id`,`nombre`,`seqorder`,`icon`,`path`) VALUES (9,1,'Región',1,'','/region');
INSERT INTO seg_menu (`seg_menu_id`,`parent_id`,`nombre`,`seqorder`,`icon`,`path`) VALUES (10,1,'Provincia',2,'','/provincia');
INSERT INTO seg_menu (`seg_menu_id`,`parent_id`,`nombre`,`seqorder`,`icon`,`path`) VALUES (11,1,'Distrito',3,'','/distrito');
INSERT INTO seg_menu (`seg_menu_id`,`parent_id`,`nombre`,`seqorder`,`icon`,`path`) VALUES (12,1,'Tipo de documento de identidad',4,'','/tipo-documento');
INSERT INTO seg_menu (`seg_menu_id`,`parent_id`,`nombre`,`seqorder`,`icon`,`path`) VALUES (13,1,'Moneda',5,'','/moneda');
INSERT INTO seg_menu (`seg_menu_id`,`parent_id`,`nombre`,`seqorder`,`icon`,`path`) VALUES (14,1,'Empresa',6,'','/empresa');
INSERT INTO seg_menu (`seg_menu_id`,`parent_id`,`nombre`,`seqorder`,`icon`,`path`) VALUES (15,1,'Unidad de medida',7,'','/unidad-medida');
INSERT INTO seg_menu (`seg_menu_id`,`parent_id`,`nombre`,`seqorder`,`icon`,`path`) VALUES (16,2,'Perfiles',1,'','/perfil');
INSERT INTO seg_menu (`seg_menu_id`,`parent_id`,`nombre`,`seqorder`,`icon`,`path`) VALUES (17,2,'Acciones',2,'','/accion');
INSERT INTO seg_menu (`seg_menu_id`,`parent_id`,`nombre`,`seqorder`,`icon`,`path`) VALUES (18,2,'Opciones de menú',3,'','/menu');
INSERT INTO seg_menu (`seg_menu_id`,`parent_id`,`nombre`,`seqorder`,`icon`,`path`) VALUES (19,2,'Permisos',4,'','/permiso');
INSERT INTO seg_menu (`seg_menu_id`,`parent_id`,`nombre`,`seqorder`,`icon`,`path`) VALUES (20,2,'Usuarios',5,'','/usuario');
INSERT INTO seg_menu (`seg_menu_id`,`parent_id`,`nombre`,`seqorder`,`icon`,`path`) VALUES (21,3,'Usuarios',1,'','/usuario-empresa');
INSERT INTO seg_menu (`seg_menu_id`,`parent_id`,`nombre`,`seqorder`,`icon`,`path`) VALUES (22,3,'Tipo de comprobante',2,'','/tipo-comprobante');
INSERT INTO seg_menu (`seg_menu_id`,`parent_id`,`nombre`,`seqorder`,`icon`,`path`) VALUES (23,3,'Numeración de comprobante',3,'','/numcomprobante');
INSERT INTO seg_menu (`seg_menu_id`,`parent_id`,`nombre`,`seqorder`,`icon`,`path`) VALUES (24,3,'Forma de pago',4,'','/forma-pago');
INSERT INTO seg_menu (`seg_menu_id`,`parent_id`,`nombre`,`seqorder`,`icon`,`path`) VALUES (25,3,'Cajas',5,'','/caja');
INSERT INTO seg_menu (`seg_menu_id`,`parent_id`,`nombre`,`seqorder`,`icon`,`path`) VALUES (26,3,'Clientes y Proveedores',5,'','/maestro');
INSERT INTO seg_menu (`seg_menu_id`,`parent_id`,`nombre`,`seqorder`,`icon`,`path`) VALUES (27,4,'Local',1,'','/local');
INSERT INTO seg_menu (`seg_menu_id`,`parent_id`,`nombre`,`seqorder`,`icon`,`path`) VALUES (28,4,'Almacén',2,'','/almacen');
INSERT INTO seg_menu (`seg_menu_id`,`parent_id`,`nombre`,`seqorder`,`icon`,`path`) VALUES (29,4,'Marca',3,'','/marca');
INSERT INTO seg_menu (`seg_menu_id`,`parent_id`,`nombre`,`seqorder`,`icon`,`path`) VALUES (30,4,'Categoría',4,'','/categoria');
INSERT INTO seg_menu (`seg_menu_id`,`parent_id`,`nombre`,`seqorder`,`icon`,`path`) VALUES (31,4,'Sub categoria',5,'','/subcategoria');
INSERT INTO seg_menu (`seg_menu_id`,`parent_id`,`nombre`,`seqorder`,`icon`,`path`) VALUES (32,4,'Unidad de medida',6,'','/unidadmedida');
INSERT INTO seg_menu (`seg_menu_id`,`parent_id`,`nombre`,`seqorder`,`icon`,`path`) VALUES (33,4,'Producto',7,'','/producto');
INSERT INTO seg_menu (`seg_menu_id`,`parent_id`,`nombre`,`seqorder`,`icon`,`path`) VALUES (34,4,'Compra',8,'','/compra');
INSERT INTO seg_menu (`seg_menu_id`,`parent_id`,`nombre`,`seqorder`,`icon`,`path`) VALUES (35,5,'Venta',1,'','/venta');
INSERT INTO seg_menu (`seg_menu_id`,`parent_id`,`nombre`,`seqorder`,`icon`,`path`) VALUES (36,5,'Listado de ventas',2,'','/list-ventas');
INSERT INTO seg_menu (`seg_menu_id`,`parent_id`,`nombre`,`seqorder`,`icon`,`path`) VALUES (37,6,'Turno de caja',1,'','/act-caja-turno');
INSERT INTO seg_menu (`seg_menu_id`,`parent_id`,`nombre`,`seqorder`,`icon`,`path`) VALUES (38,6,'Cuentas por pagar',3,'','/cuentas-pagar');
INSERT INTO seg_menu (`seg_menu_id`,`parent_id`,`nombre`,`seqorder`,`icon`,`path`) VALUES (39,6,'Cuentas por cobrar',4,'','/cuentas-cobrar');
INSERT INTO seg_menu (`seg_menu_id`,`parent_id`,`nombre`,`seqorder`,`icon`,`path`) VALUES (40,7,'Reporte ventas',1,'','/rpt-ventas');
INSERT INTO seg_menu (`seg_menu_id`,`parent_id`,`nombre`,`seqorder`,`icon`,`path`) VALUES (41,7,'Reporte compras',2,'','/rpt-compras');
INSERT INTO seg_menu (`seg_menu_id`,`parent_id`,`nombre`,`seqorder`,`icon`,`path`) VALUES (42,7,'Stock valorizado',1,'','/rpt-almacen');
INSERT INTO seg_menu (`seg_menu_id`,`parent_id`,`nombre`,`seqorder`,`icon`,`path`) VALUES (43,7,'Kardex valorizado',1,'','/rpt-movimiento-producto');
INSERT INTO seg_menu (`seg_menu_id`,`parent_id`,`nombre`,`seqorder`,`icon`,`path`) VALUES (44,6,'Operaciones de Caja',2,'','/caja-operacion');
INSERT INTO seg_menu (`seg_menu_id`,`parent_id`,`nombre`,`seqorder`,`icon`,`path`) VALUES (45,7,'Cuentas pendientes',5,'','/rpt-pago-programacion');
INSERT INTO seg_menu (`seg_menu_id`,`parent_id`,`nombre`,`seqorder`,`icon`,`path`) VALUES (46,3,'Tipos de Movimiento',9,'','/tipo-mov');
INSERT INTO seg_menu (`seg_menu_id`,`parent_id`,`nombre`,`seqorder`,`icon`,`path`) VALUES (47,4,'Movimiento de Almacén',9,'','/mov-almacen');
INSERT INTO seg_menu (`seg_menu_id`,`parent_id`,`nombre`,`seqorder`,`icon`,`path`) VALUES (48,4,'Generación de códigos de barras',11,'','/producto-list');


INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (2,1,1,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (4,1,3,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (5,1,4,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (6,1,5,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (7,1,6,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (8,1,7,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (9,1,9,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (10,1,10,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (11,1,11,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (12,1,12,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (13,1,13,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (14,1,14,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (15,1,15,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (21,1,21,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (22,1,22,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (23,1,23,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (24,1,24,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (25,1,25,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (26,1,26,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (27,1,27,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (28,1,28,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (29,1,29,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (30,1,30,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (31,1,31,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (32,1,32,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (33,1,33,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (34,1,34,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (35,1,35,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (36,1,36,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (37,1,37,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (38,1,38,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (39,1,39,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (40,1,40,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (41,1,41,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (42,1,42,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (43,1,43,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (46,2,4,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (47,2,5,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (48,2,6,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (49,2,7,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (50,2,21,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (52,2,23,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (53,2,24,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (54,2,25,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (55,2,26,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (56,2,27,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (57,2,28,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (58,2,29,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (59,2,30,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (60,2,31,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (61,2,32,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (62,2,33,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (63,2,34,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (64,2,35,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (65,2,36,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (66,2,37,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (67,2,38,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (68,2,39,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (69,2,40,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (70,2,41,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (71,2,42,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (72,2,43,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (73,2,44,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (74,1,44,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (75,1,45,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (76,2,45,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (77,2,47,1);
INSERT INTO seg_permiso (`seg_permiso_id`,`seg_rol_id`,`seg_menu_id`,`seg_accion_id`) VALUES (78,2,48,1);

INSERT INTO cnf_forma_pago (`cnf_forma_pago_id`,`nombre`,`flag_estado`,`cnf_empresa_id`,`tipo`) VALUES (1,'Contado',NULL,1,NULL);
INSERT INTO cnf_forma_pago (`cnf_forma_pago_id`,`nombre`,`flag_estado`,`cnf_empresa_id`,`tipo`) VALUES (2,'Credito',NULL,1,NULL);
INSERT INTO cnf_forma_pago (`cnf_forma_pago_id`,`nombre`,`flag_estado`,`cnf_empresa_id`,`tipo`) VALUES (7,'CONTADO',NULL,2,1);
INSERT INTO cnf_forma_pago (`cnf_forma_pago_id`,`nombre`,`flag_estado`,`cnf_empresa_id`,`tipo`) VALUES (8,'CREDITO',NULL,2,1);
INSERT INTO cnf_forma_pago (`cnf_forma_pago_id`,`nombre`,`flag_estado`,`cnf_empresa_id`,`tipo`) VALUES (9,'FACTURA A 60 DIAS',NULL,2,1);
INSERT INTO cnf_forma_pago (`cnf_forma_pago_id`,`nombre`,`flag_estado`,`cnf_empresa_id`,`tipo`) VALUES (10,'MENSUAL',NULL,2,2);
INSERT INTO cnf_forma_pago (`cnf_forma_pago_id`,`nombre`,`flag_estado`,`cnf_empresa_id`,`tipo`) VALUES (11,'MENSUAL',NULL,1,2);

INSERT INTO cnf_forma_pago_detalle (`cnf_forma_pago_detalle_id`,`cnf_forma_pago_id`,`modo_dias_intervalo`,`modo_porcentaje`,`modo_monto`,`modo_dia_vencimiento`) VALUES (2,9,30,50.00,NULL,NULL);
INSERT INTO cnf_forma_pago_detalle (`cnf_forma_pago_detalle_id`,`cnf_forma_pago_id`,`modo_dias_intervalo`,`modo_porcentaje`,`modo_monto`,`modo_dia_vencimiento`) VALUES (3,9,60,50.00,NULL,NULL);
INSERT INTO cnf_forma_pago_detalle (`cnf_forma_pago_detalle_id`,`cnf_forma_pago_id`,`modo_dias_intervalo`,`modo_porcentaje`,`modo_monto`,`modo_dia_vencimiento`) VALUES (4,10,NULL,NULL,100.00,31);
INSERT INTO cnf_forma_pago_detalle (`cnf_forma_pago_detalle_id`,`cnf_forma_pago_id`,`modo_dias_intervalo`,`modo_porcentaje`,`modo_monto`,`modo_dia_vencimiento`) VALUES (6,11,NULL,NULL,50.00,31);
INSERT INTO cnf_forma_pago_detalle (`cnf_forma_pago_detalle_id`,`cnf_forma_pago_id`,`modo_dias_intervalo`,`modo_porcentaje`,`modo_monto`,`modo_dia_vencimiento`) VALUES (7,10,NULL,NULL,NULL,31);

INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (1,'NIU','UNIDAD (BIENES)','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (3,'4A','BOBINAS','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (4,'BJ','BALDE','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (5,'BLL','BARRILES','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (6,'BG','BOLSA','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (7,'BO','BOTELLAS','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (8,'BX','CAJA','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (9,'CT','CARTONES','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (10,'CMK','CENTIMETRO CUADRADO','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (11,'CMQ','CENTIMETRO CUBICO','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (12,'CMT','CENTIMETRO LINEAL','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (13,'CEN','CIENTO DE UNIDADES','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (14,'CY','CILINDRO','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (15,'CJ','CONOS','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (16,'DZN','DOCENA','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (17,'DZP','DOCENA POR 10**6','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (18,'BE','FARDO','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (19,'GLI','GALON INGLES (4,545956L)','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (20,'GRM','GRAMO','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (21,'GRO','GRUESA','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (22,'HLT','HECTOLITRO','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (23,'LEF','HOJA','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (24,'SET','JUEGO','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (25,'KGM','KILOGRAMO','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (26,'KTM','KILOMETRO','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (27,'KWH','KILOVATIO HORA','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (28,'KT','KIT','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (29,'CA','LATAS','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (30,'LBR','LIBRAS','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (31,'LTR','LITRO','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (32,'MWH','MEGAWATT HORA','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (33,'MTR','METRO','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (34,'MTK','METRO CUADRADO','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (35,'MTQ','METRO CUBICO','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (36,'MGM','MILIGRAMOS','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (37,'MLT','MILILITRO','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (38,'MMT','MILIMETRO','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (39,'MMK','MILIMETRO CUADRADO','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (40,'MMQ','MILIMETRO CUBICO','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (41,'MLL','MILLARES','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (42,'UM','MILLON DE UNIDADES','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (43,'ONZ','ONZAS','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (44,'PF','PALETAS','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (45,'PK','PAQUETE','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (46,'PR','PAR','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (47,'FOT','PIES','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (48,'FTK','PIES CUADRADOS','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (49,'FTQ','PIES CUBICOS','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (50,'C62','PIEZAS','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (51,'PG','PLACAS','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (52,'ST','PLIEGO','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (53,'INH','PULGADAS','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (54,'RM','RESMA','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (55,'DR','TAMBOR','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (56,'STN','TONELADA CORTA','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (57,'LTN','TONELADA LARGA','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (58,'TNE','TONELADAS','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (59,'TU','TUBOS','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (61,'ZZ','UNIDAD (SERVICIOS)','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (62,'GLL','US GALON (3,7843 L)','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (63,'YRD','YARDA','1');
INSERT INTO cnf_unidad_medida (`cnf_unidad_medida_id`,`codigo_sunat`,`nombre`,`flag_estado`) VALUES (64,'YDK','YARDA CUADRADA','1');

INSERT INTO inv_tipo_mov_almacen(inv_tipo_mov_almacen_id,nombre,codigo_sunat,naturaleza) values(null,'VENTA NACIONAL','01','-1');
INSERT INTO inv_tipo_mov_almacen(inv_tipo_mov_almacen_id,nombre,codigo_sunat,naturaleza) values(null,'COMPRA NACIONAL','02','1');
INSERT INTO inv_tipo_mov_almacen(inv_tipo_mov_almacen_id,nombre,codigo_sunat,naturaleza) values(null,'CONSIGNACIÓN RECIBIDA','03','1');
INSERT INTO inv_tipo_mov_almacen(inv_tipo_mov_almacen_id,nombre,codigo_sunat,naturaleza) values(null,'CONSIGNACIÓN ENTREGADA','04','-1');
INSERT INTO inv_tipo_mov_almacen(inv_tipo_mov_almacen_id,nombre,codigo_sunat,naturaleza) values(null,'DEVOLUCIÓN RECIBIDA','05','1');
INSERT INTO inv_tipo_mov_almacen(inv_tipo_mov_almacen_id,nombre,codigo_sunat,naturaleza) values(null,'DEVOLUCIÓN ENTREGADA','06','-1');
INSERT INTO inv_tipo_mov_almacen(inv_tipo_mov_almacen_id,nombre,codigo_sunat,naturaleza) values(null,'BONIFICACIÓN','07','-1');
INSERT INTO inv_tipo_mov_almacen(inv_tipo_mov_almacen_id,nombre,codigo_sunat,naturaleza) values(null,'PREMIO','08','-1');
INSERT INTO inv_tipo_mov_almacen(inv_tipo_mov_almacen_id,nombre,codigo_sunat,naturaleza) values(null,'DONACIÓN','09','-1');
INSERT INTO inv_tipo_mov_almacen(inv_tipo_mov_almacen_id,nombre,codigo_sunat,naturaleza) values(null,'SALIDA A PRODUCCIÓN','10','1');
INSERT INTO inv_tipo_mov_almacen(inv_tipo_mov_almacen_id,nombre,codigo_sunat,naturaleza) values(null,'SALIDA POR TRANSFERENCIA ENTRE ALMACENES ','11','-1');
INSERT INTO inv_tipo_mov_almacen(inv_tipo_mov_almacen_id,nombre,codigo_sunat,naturaleza) values(null,'RETIRO','12','-1');
INSERT INTO inv_tipo_mov_almacen(inv_tipo_mov_almacen_id,nombre,codigo_sunat,naturaleza) values(null,'MERMAS','13','-1');
INSERT INTO inv_tipo_mov_almacen(inv_tipo_mov_almacen_id,nombre,codigo_sunat,naturaleza) values(null,'DESMEDROS','14','-1');
INSERT INTO inv_tipo_mov_almacen(inv_tipo_mov_almacen_id,nombre,codigo_sunat,naturaleza) values(null,'DESTRUCCIÓN','15','-1');
INSERT INTO inv_tipo_mov_almacen(inv_tipo_mov_almacen_id,nombre,codigo_sunat,naturaleza) values(null,'SALDO INICIAL','16','1');
INSERT INTO inv_tipo_mov_almacen(inv_tipo_mov_almacen_id,nombre,codigo_sunat,naturaleza) values(null,'EXPORTACIÓN','17','-1');
INSERT INTO inv_tipo_mov_almacen(inv_tipo_mov_almacen_id,nombre,codigo_sunat,naturaleza) values(null,'IMPORTACIÓN','18','1');
INSERT INTO inv_tipo_mov_almacen(inv_tipo_mov_almacen_id,nombre,codigo_sunat,naturaleza) values(null,'ENTRADA DE PRODUCCIÓN','19','1');
INSERT INTO inv_tipo_mov_almacen(inv_tipo_mov_almacen_id,nombre,codigo_sunat,naturaleza) values(null,'ENTRADA POR DEVOLUCIÓN DE PRODUCCIÓN','20','1');
INSERT INTO inv_tipo_mov_almacen(inv_tipo_mov_almacen_id,nombre,codigo_sunat,naturaleza) values(null,'ENTRADA POR TRANSFERENCIA ENTRE ALMACENES ','21','1');
INSERT INTO inv_tipo_mov_almacen(inv_tipo_mov_almacen_id,nombre,codigo_sunat,naturaleza) values(null,'ENTRADA POR IDENTIFICACIÓN ERRONEA','22','1');
INSERT INTO inv_tipo_mov_almacen(inv_tipo_mov_almacen_id,nombre,codigo_sunat,naturaleza) values(null,'SALIDA POR IDENTIFICACIÓN ERRONEA','23','-1');
INSERT INTO inv_tipo_mov_almacen(inv_tipo_mov_almacen_id,nombre,codigo_sunat,naturaleza) values(null,'ENTRADA POR DEVOLUCIÓN DEL CLIENTE','24','1');
INSERT INTO inv_tipo_mov_almacen(inv_tipo_mov_almacen_id,nombre,codigo_sunat,naturaleza) values(null,'SALIDA POR DEVOLUCIÓN AL PROVEEDOR','25','-1');
INSERT INTO inv_tipo_mov_almacen(inv_tipo_mov_almacen_id,nombre,codigo_sunat,naturaleza) values(null,'ENTRADA PARA SERVICIO DE PRODUCCIÓN','26','1');
INSERT INTO inv_tipo_mov_almacen(inv_tipo_mov_almacen_id,nombre,codigo_sunat,naturaleza) values(null,'SALIDA POR SERVICIO DE PRODUCCIÓN','27','-1');
INSERT INTO inv_tipo_mov_almacen(inv_tipo_mov_almacen_id,nombre,codigo_sunat,naturaleza) values(null,'AJUSTE POR DIFERENCIA DE INVENTARIO','28','1');
INSERT INTO inv_tipo_mov_almacen(inv_tipo_mov_almacen_id,nombre,codigo_sunat,naturaleza) values(null,'ENTRADA DE BIENES EN PRÉSTAMO','29','1');
INSERT INTO inv_tipo_mov_almacen(inv_tipo_mov_almacen_id,nombre,codigo_sunat,naturaleza) values(null,'SALIDA DE BIENES EN PRÉSTAMO','30','-1');
INSERT INTO inv_tipo_mov_almacen(inv_tipo_mov_almacen_id,nombre,codigo_sunat,naturaleza) values(null,'ENTRADA DE BIENES EN CUSTODIA','31','1');
INSERT INTO inv_tipo_mov_almacen(inv_tipo_mov_almacen_id,nombre,codigo_sunat,naturaleza) values(null,'SALIDA DE BIENES EN CUSTODIA','32','-1');
INSERT INTO inv_tipo_mov_almacen(inv_tipo_mov_almacen_id,nombre,codigo_sunat,naturaleza) values(null,'MUESTRAS MÉDICAS','33','-1');
INSERT INTO inv_tipo_mov_almacen(inv_tipo_mov_almacen_id,nombre,codigo_sunat,naturaleza) values(null,'PUBLICIDAD','34','-1');
INSERT INTO inv_tipo_mov_almacen(inv_tipo_mov_almacen_id,nombre,codigo_sunat,naturaleza) values(null,'GASTOS DE REPRESENTACIÓN','35','-1');
INSERT INTO inv_tipo_mov_almacen(inv_tipo_mov_almacen_id,nombre,codigo_sunat,naturaleza) values(null,'RETIRO PARA ENTREGA A TRABAJADORES','36','-1');
INSERT INTO inv_tipo_mov_almacen(inv_tipo_mov_almacen_id,nombre,codigo_sunat,naturaleza) values(null,'RETIRO POR CONVENIO COLECTIVO','37','-1');
INSERT INTO inv_tipo_mov_almacen(inv_tipo_mov_almacen_id,nombre,codigo_sunat,naturaleza) values(null,'RETIRO POR SUSTITUCIÓN DE BIEN SINIESTRADO','38','-1');
INSERT INTO inv_tipo_mov_almacen(inv_tipo_mov_almacen_id,nombre,codigo_sunat,naturaleza) values(null,'OTROS INGRESOS','91','1');
INSERT INTO inv_tipo_mov_almacen(inv_tipo_mov_almacen_id,nombre,codigo_sunat,naturaleza) values(null,'OTRAS SALIDAS','92','-1');
INSERT INTO inv_tipo_mov_almacen(inv_tipo_mov_almacen_id,nombre,codigo_sunat,naturaleza) values(null,'OTROS 3','93','');
INSERT INTO inv_tipo_mov_almacen(inv_tipo_mov_almacen_id,nombre,codigo_sunat,naturaleza) values(null,'OTROS 4','94','');
INSERT INTO inv_tipo_mov_almacen(inv_tipo_mov_almacen_id,nombre,codigo_sunat,naturaleza) values(null,'OTROS 5','95','');
INSERT INTO inv_tipo_mov_almacen(inv_tipo_mov_almacen_id,nombre,codigo_sunat,naturaleza) values(null,'OTROS 6','96','');
INSERT INTO inv_tipo_mov_almacen(inv_tipo_mov_almacen_id,nombre,codigo_sunat,naturaleza) values(null,'OTROS 7','97','');
INSERT INTO inv_tipo_mov_almacen(inv_tipo_mov_almacen_id,nombre,codigo_sunat,naturaleza) values(null,'OTROS 8','98','');
INSERT INTO inv_tipo_mov_almacen(inv_tipo_mov_almacen_id,nombre,codigo_sunat,naturaleza) values(null,'OTROS ','99','');


