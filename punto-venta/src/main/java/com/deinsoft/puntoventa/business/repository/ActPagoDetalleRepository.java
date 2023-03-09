package com.deinsoft.puntoventa.business.repository;

import com.deinsoft.puntoventa.business.model.ActPago;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.ActPagoDetalle;

public interface ActPagoDetalleRepository extends JpaRepository<ActPagoDetalle,Long> {
	@Query(value="select p from actPagoDetalle p ")

	List<ActPagoDetalle> getAllActPagoDetalle();
	@Query(value="select p from actPagoDetalle p "+
			"where p.actPago.id =  ?1 ")
	List<ActPagoDetalle>findByActPagoId(long id);

	@Query(value="select p from actPagoDetalle p "+
			"where p.actPagoProgramacion.id =  ?1 ")
	List<ActPagoDetalle>findByActPagoProgramacionId(long id);

        void deleteByActPago(ActPago actPago);
}
