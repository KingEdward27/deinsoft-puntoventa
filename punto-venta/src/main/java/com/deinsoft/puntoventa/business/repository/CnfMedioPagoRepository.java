package com.deinsoft.puntoventa.business.repository;

import com.deinsoft.puntoventa.business.model.CnfMedioPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CnfMedioPagoRepository extends JpaRepository<CnfMedioPago,Long> {
	@Query(value="select p from cnfMedioPago p "+ 
 "where upper(p.nombre) like %?1% and upper(p.flagEstado) like %?2% ")

	List<CnfMedioPago> getAllCnfMedioPago(String nombre,String flagEstado);
	@Query(value="select p from cnfMedioPago p "+
			"where p.cnfEmpresa.id =  ?1 ")
	List<CnfMedioPago>findByCnfEmpresaId(long id);


}
