package com.deinsoft.puntoventa.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.CnfPlanContrato;

public interface CnfPlanContratoRepository extends JpaRepository<CnfPlanContrato,Long> {
	@Query(value="select p from cnfPlanContrato p "+ 
 "where upper(p.nombre) like %?1% ")

	List<CnfPlanContrato> getAllCnfPlanContrato(String nombre);
	@Query(value="select p from cnfPlanContrato p "+
			"where p.cnfEmpresa.id =  ?1 ")
	List<CnfPlanContrato>findByCnfEmpresaId(long id);


}
