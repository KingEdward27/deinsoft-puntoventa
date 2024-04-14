package com.deinsoft.puntoventa.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.ActOrdenDetalle;

public interface ActOrdenDetalleRepository extends JpaRepository<ActOrdenDetalle,Long> {
	@Query(value="select p from actOrdenDetalle p "+ 
 "where upper(p.descripcion) like %?1% ")

	List<ActOrdenDetalle> getAllActOrdenDetalle(String descripcion);
	@Query(value="select p from actOrdenDetalle p "+
			"where p.actOrden.id =  ?1 ")
	List<ActOrdenDetalle>findByActOrdenId(long id);

	@Query(value="select p from actOrdenDetalle p "+
			"where p.cnfProducto.id =  ?1 ")
	List<ActOrdenDetalle>findByCnfProductoId(long id);

	@Query(value="select p from actOrdenDetalle p "+
			"where p.cnfImpuestoCondicion.id =  ?1 ")
	List<ActOrdenDetalle>findByCnfImpuestoCondicionId(long id);


}
