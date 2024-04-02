SELECT * FROM dbpuntoventa.act_comprobante_detalle;

select p.cnf_producto_id,p.nombre,p.precio,p.costo,count(*),sum(ac.importe),sum(ac.precio - p.costo) from act_comprobante c 
inner join act_comprobante_detalle ac on c.act_comprobante_id = ac.act_comprobante_id
inner join cnf_producto p on ac.cnf_producto_id = p.cnf_producto_id
where c.fecha between '2024-03-01' and '2024-03-31'
and c.flag_estado = '2' and c.flag_isventa = '1'
group by p.cnf_producto_id,p.nombre,p.precio,p.costo

select c.serie,c.numero, p.cnf_producto_id,p.precio,p.costo,ac.precio,p.nombre from act_comprobante c 
inner join act_comprobante_detalle ac on c.act_comprobante_id = ac.act_comprobante_id
inner join cnf_producto p on ac.cnf_producto_id = p.cnf_producto_id
where c.fecha between '2024-03-01' and '2024-03-31'
and c.flag_estado = '2' and c.flag_isventa = '1' and p.cnf_producto_id = 13