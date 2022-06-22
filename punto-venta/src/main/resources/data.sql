
INSERT INTO deinsoft_empresa
(idempresa,
cert_name,
cert_pass,
clavesol,
direccion,
estado,
nombre_comercial,
numdoc,
razon_social,
serie,
tipodoc,
token,
usuariosol)
VALUES
(1,
'',
'',
'',
'',
'1',
'EMPRESA DE PRUEBA',
'12345678909',
'EMPRESA DE PRUEBA SRL',
'',
'6',
'',
'');

insert into deinsoft_cnf_tipo_documento values(1,"DNI","1");
insert into deinsoft_cnf_tipo_documento values(2,"RUC","6");

insert into deinsoft_cnf_empresa(cnf_empresa_id,nombre,descripcion,cnf_tipo_documento_id,nro_documento,telefono,direccion,token) 
values(null,'EMPRESA PRUEBA',"1");
insert into deinsoft_cnf_empresa values(2,"RUC","6");

insert into deinsoft_seg_rol values(1,"ROLE_ADMIN","ROLE_ADMIN");
insert into deinsoft_seg_rol values(2,"ROLE_USER","ROLE_USER");

insert into deinsoft_seg_usuario (seg_usuario_id,email,nombre,password,isactive) values(1,"edward21.sistemas@gmail.com",'admin','$2a$10$ySSHW/94asbWwyduiUu6t./z761Sqgo0kJA9Q0/DexTBv9wY267wu','1');
insert into deinsoft_seg_usuario (seg_usuario_id,email,nombre,password,isactive) values(2,"edward21.sistemas@gmail.com",'pablo','$2a$10$ySSHW/94asbWwyduiUu6t./z761Sqgo0kJA9Q0/DexTBv9wY267wu','1');
insert into deinsoft_seg_usuario (seg_usuario_id,email,nombre,password,isactive) values(3,"antonio@gmail.com",'antonio','$2a$10$ySSHW/94asbWwyduiUu6t./z761Sqgo0kJA9Q0/DexTBv9wY267wu','1');

insert into deinsoft_seg_rol_usuario values(1,1,1,1);
-- insert into seg_rol_usuario values(null,1,2,2);
-- insert into seg_rol_usuario values(null,6,2,3);

-- select * from deinsoft_empresa 
-- select * from deinsoft_seg_rol
-- select * from deinsoft_seg_usuario
-- select * from seg_rol_usuario