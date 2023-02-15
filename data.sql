INSERT INTO cnf_tipo_documento (`cnf_tipo_documento_id`,`abreviatura`,`nombre`,`codigo_sunat`,`flag_estado`,`name`,`value`) VALUES (2,'DNI','DOCUMENTO NACIONAL DE IDENTIDAD','1',NULL,'',NULL);
INSERT INTO cnf_tipo_documento (`cnf_tipo_documento_id`,`abreviatura`,`nombre`,`codigo_sunat`,`flag_estado`,`name`,`value`) VALUES (3,'RUC','REGISTRO ÚNICO DE CONTRIBUYENTE','6',NULL,'',NULL);

INSERT INTO cnf_tipo_comprobante (`cnf_tipo_comprobante_id`,`nombre`,`codigo_sunat`,`codigo`,`flag_electronico`) VALUES (1,'BOLETA','03','BOL','1');
INSERT INTO cnf_tipo_comprobante (`cnf_tipo_comprobante_id`,`nombre`,`codigo_sunat`,`codigo`,`flag_electronico`) VALUES (2,'FACTURA','01','FAC','1');
INSERT INTO cnf_tipo_comprobante (`cnf_tipo_comprobante_id`,`nombre`,`codigo_sunat`,`codigo`,`flag_electronico`) VALUES (4,'NOTA DE VENTA','00','NOT','0');
INSERT INTO cnf_tipo_comprobante (`cnf_tipo_comprobante_id`,`nombre`,`codigo_sunat`,`codigo`,`flag_electronico`) VALUES (5,'NOTA DE SALIDA','','NSA','0');
INSERT INTO cnf_tipo_comprobante (`cnf_tipo_comprobante_id`,`nombre`,`codigo_sunat`,`codigo`,`flag_electronico`) VALUES (6,'NOTA DE ENTRADA','','NEA','0');

INSERT INTO seg_rol (`seg_rol_id`,`descripcion`,`nombre`) VALUES (1,NULL,'ROLE_ADMIN');
INSERT INTO seg_rol (`seg_rol_id`,`descripcion`,`nombre`) VALUES (2,NULL,'ROLE_USER');

INSERT INTO cnf_empresa (`cnf_empresa_id`,`cnf_tipo_documento_id`,`nombre`,`descripcion`,`nro_documento`,`direccion`,`telefono`,`empresacol`,`cnf_distrito_id`,`estado`,`token`,`ruta_pse`) 
VALUES (1,3,'DESARROLLO INTEGRAL DE SOFTWARE','DEINSOFT','20534999616','JR LOS CLAVELES 123','1322323',NULL,1004,NULL,NULL,NULL);
INSERT INTO cnf_empresa (`cnf_empresa_id`,`cnf_tipo_documento_id`,`nombre`,`descripcion`,`nro_documento`,`direccion`,`telefono`,`empresacol`,`cnf_distrito_id`,`estado`,`token`,`ruta_pse`) 
VALUES (2,3,'BATERIAS DON TELE','BATERIAS DON TELE','12345678901','AV PANAMERICANA SUR 1332 - A','',NULL,1004,'1','Bearer eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE2NTA5MjAwMTYsImlzcyI6IkRFSU5TT0ZUIiwianRpIjoiREVGQUNULUpXVCIsInN1YiI6IjEwNDE0MzE2NTk1L1BFUkVaIERFTEdBRE8gQkxBTkNBIE5FUkkiLCJudW1Eb2MiOiIxMDQxNDMxNjU5NSIsInJhem9uU29jaWFsIjoiUEVSRVogREVMR0FETyBCTEFOQ0EgTkVSSSIsInVzdWF','http://localhost:8080/api/v1/document/send-document');

INSERT INTO seg_usuario (`seg_usuario_id`,`nombre`,`email`,`password`,`estado`,`cnf_empresa_id`,`cnf_empresa`) 
VALUES (1,'admin','edward21.sistemas@gmail.com','$2a$10$ySSHW/94asbWwyduiUu6t./z761Sqgo0kJA9Q0/DexTBv9wY267wu',1,1,NULL);
INSERT INTO seg_usuario (`seg_usuario_id`,`nombre`,`email`,`password`,`estado`,`cnf_empresa_id`,`cnf_empresa`) 
VALUES (3,'antonio','facturacionelectronica@opendeinsoft.com','$2a$10$ySSHW/94asbWwyduiUu6t./z761Sqgo0kJA9Q0/DexTBv9wY267wu',1,1,NULL);
INSERT INTO seg_usuario (`seg_usuario_id`,`nombre`,`email`,`password`,`estado`,`cnf_empresa_id`,`cnf_empresa`) 
VALUES (4,'diana','dmra2001@gmail.com','$2a$10$PXjaRqL5mK1fp4kwGdtcO.i53gTzEbRnGOJUHCt4vKlVefdpks0HC',1,2,NULL);
INSERT INTO seg_usuario (`seg_usuario_id`,`nombre`,`email`,`password`,`estado`,`cnf_empresa_id`,`cnf_empresa`) 
VALUES (7,'diana2','edward21.sistemas2@gmail.com','$2a$10$1o1fhysFm2i/ZLZwH.mGA.g8XK4u3qT54kNomb9mPZ2/DmOo7VaZC',1,2,NULL);

INSERT INTO seg_accion (seg_accion_id,nombre) values (1,'VER OPCIÓN');

INSERT INTO `deinsoftcloud`.`cnf_local` (`nombre`, `cnf_empresa_id`) VALUES ('LOCAL PRINCIPAL', '1');
INSERT INTO `deinsoftcloud`.`cnf_local` (`nombre`, `cnf_empresa_id`) VALUES ('LOCAL PRINCIPAL', '2');

INSERT INTO seg_rol_usuario (`seg_rol_usuario_id`,`seg_rol_id`,`seg_usuario_id`,`cnf_empresa_id`,`cnf_local_id`) VALUES (1,1,1,1,NULL);
INSERT INTO seg_rol_usuario (`seg_rol_usuario_id`,`seg_rol_id`,`seg_usuario_id`,`cnf_empresa_id`,`cnf_local_id`) VALUES (2,2,3,1,NULL);
INSERT INTO seg_rol_usuario (`seg_rol_usuario_id`,`seg_rol_id`,`seg_usuario_id`,`cnf_empresa_id`,`cnf_local_id`) VALUES (3,1,4,2,NULL);
INSERT INTO seg_rol_usuario (`seg_rol_usuario_id`,`seg_rol_id`,`seg_usuario_id`,`cnf_empresa_id`,`cnf_local_id`) VALUES (6,2,7,2,2);

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

