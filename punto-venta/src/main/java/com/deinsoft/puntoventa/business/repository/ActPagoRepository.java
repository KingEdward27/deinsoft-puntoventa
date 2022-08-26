package com.deinsoft.puntoventa.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.ActPago;

public interface ActPagoRepository extends JpaRepository<ActPago,Long> {
	@Query(value="select p from actPago p ")

	List<ActPago> getAllActPago();
	@Query(value="select p from actPago p "+
			"where p.actPagoProgramacion.id =  ?1 ")
	List<ActPago>findByActPagoProgramacionId(long id);


}
