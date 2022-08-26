package com.deinsoft.puntoventa.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.ActPagoProgramacion;

public interface ActPagoProgramacionRepository extends JpaRepository<ActPagoProgramacion,Long> {
	@Query(value="select p from actPagoProgramacion p ")

	List<ActPagoProgramacion> getAllActPagoProgramacion();
	@Query(value="select p from actPagoProgramacion p "+
			"where p.actComprobante.id =  ?1 ")
	List<ActPagoProgramacion>findByActComprobanteId(long id);


}
