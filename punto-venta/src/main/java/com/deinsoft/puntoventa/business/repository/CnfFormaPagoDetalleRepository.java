package com.deinsoft.puntoventa.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.CnfFormaPagoDetalle;

public interface CnfFormaPagoDetalleRepository extends JpaRepository<CnfFormaPagoDetalle,Long> {
	@Query(value="select p from cnfFormaPagoDetalle p ")

	List<CnfFormaPagoDetalle> getAllCnfFormaPagoDetalle();
	@Query(value="select p from cnfFormaPagoDetalle p "+
			"where p.cnfFormaPago.id =  ?1 ")
	List<CnfFormaPagoDetalle>findByCnfFormaPagoId(long id);


}
