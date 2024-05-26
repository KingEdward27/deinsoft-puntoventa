package com.deinsoft.puntoventa.business.repository;

import com.deinsoft.puntoventa.business.bean.ParamBean;
import com.deinsoft.puntoventa.business.dto.SecurityFilterDto;
import com.deinsoft.puntoventa.business.model.ActComprobante;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.ActContrato;
import com.deinsoft.puntoventa.business.model.ActPagoProgramacion;

public interface ActContratoRepository extends JpaRepository<ActContrato,Long> {
	@Query(value="select p from actContrato p "+ 
                "where p.cnfLocal.cnfEmpresa.id = ?1 and upper(p.serie) like %?2% and upper(p.numero) like %?3% "
                + "and upper(p.flagEstado) like %?4% and upper(p.nroPoste) like %?5% and upper(p.urlMap) like %?6% and upper(p.direccion) like %?7% "
                + "and p.cnfLocal.id in :#{#securityFilterDto.localIds}")
	List<ActContrato> getAllActContrato(long cnfLocalId, String serie,String numero,String flagEstado,String nroPoste,
                String urlMap,String direccion,@Param("securityFilterDto") SecurityFilterDto securityFilterDto);
        
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

	List<ActContrato>findByCnfMaestroIdAndCnfPlanContratoId(long id, long idPlan);
        
        @Query(value = "select distinct p from actContrato p "
                + "where ((:#{#paramBean.direccion} = '' or :#{#paramBean.direccion} = null) "
                + "or lower(p.direccion) like lower(concat('%',:#{#paramBean.direccion},'%'))) "
                + "and (:#{#paramBean.cnfZona.id} = 0l or (p.cnfZona != null and p.cnfZona.id = :#{#paramBean.cnfZona.id})) "
                + "and p.cnfLocal.id in :#{#securityFilterDto.localIds}")
        List<ActContrato> getReportActContrato(@Param("paramBean") ParamBean paramBean,@Param("securityFilterDto") SecurityFilterDto securityFilterDto);

        @Query(value = "select p from actContrato p "
            + "where p.cnfLocal.cnfEmpresa.id =  ?1 and month(fecha) = ?2 and flagEstado = '1'")
        List<ActContrato> findByCnfEmpresaIdAndMonth(long id, int month);
}
