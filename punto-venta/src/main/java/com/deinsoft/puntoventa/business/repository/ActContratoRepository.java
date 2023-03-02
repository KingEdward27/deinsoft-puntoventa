package com.deinsoft.puntoventa.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.ActContrato;

public interface ActContratoRepository extends JpaRepository<ActContrato,Long> {
	@Query(value="select p from actContrato p "+ 
                "where p.cnfLocal.cnfEmpresa.id = ?1 and upper(p.serie) like %?2% and upper(p.numero) like %?3% and upper(p.observacion) like %?4% "
                + "and upper(p.flagEstado) like %?5% and upper(p.nroPoste) like %?6% and upper(p.urlMap) like %?7% ")
	List<ActContrato> getAllActContrato(long cnfLocalId, String serie,String numero,String observacion,String flagEstado,String nroPoste,String urlMap);
        
	@Query(value="select p from actContrato p "+
			"where p.cnfMaestro.id =  ?1 ")
	List<ActContrato>findByCnfMaestroId(long id);

	@Query(value="select p from actContrato p "+
			"where p.cnfLocal.id =  ?1 ")
	List<ActContrato>findByCnfLocalId(long id);

	@Query(value="select p from actContrato p "+
			"where p.cnfTipoComprobante.id =  ?1 ")
	List<ActContrato>findByCnfTipoComprobanteId(long id);

	@Query(value="select p from actContrato p "+
			"where p.cnfFormaPago.id =  ?1 ")
	List<ActContrato>findByCnfFormaPagoId(long id);

	@Query(value="select p from actContrato p "+
			"where p.cnfPlanContrato.id =  ?1 ")
	List<ActContrato>findByCnfPlanContratoId(long id);


}
