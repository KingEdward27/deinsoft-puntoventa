package com.deinsoft.puntoventa.business.repository;

import com.deinsoft.puntoventa.business.bean.ParamBean;
import com.deinsoft.puntoventa.business.dto.SecurityFilterDto;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.ActOrden;

public interface ActOrdenRepository extends JpaRepository<ActOrden, Long> {

    @Query(value = "select p from actOrden p "
            + "where upper(p.serie) like %?1% and upper(p.numero) like %?2% and upper(p.observacion) like %?3% "
            + "and upper(p.flagEstado) like %?4% and upper(p.flagIsventa) like %?5% "
            + "and p.cnfLocal.id in :#{#securityFilterDto.localIds}")
    List<ActOrden> getAllActOrden(String serie, String numero, 
                                                String observacion, String flagEstado, 
                                                String flagIsventa, @Param("securityFilterDto") SecurityFilterDto securityFilterDto);

    @Query(value = "select p from actOrden p "
            + "where p.cnfMaestro.id =  ?1 ")
    List<ActOrden> findByCnfMaestroId(long id);

    @Query(value = "select p from actOrden p "
            + "where p.cnfFormaPago.id =  ?1 ")
    List<ActOrden> findByCnfFormaPagoId(long id);

    @Query(value = "select p from actOrden p "
            + "where p.cnfMoneda.id =  ?1 ")
    List<ActOrden> findByCnfMonedaId(long id);

    @Query(value = "select p from actOrden p "
            + "where p.cnfLocal.id =  ?1 ")
    List<ActOrden> findByCnfLocalId(long id);

    @Query(value = "select p from actOrden p "
            + "where p.cnfTipoComprobante.id =  ?1 ")
    List<ActOrden> findByCnfTipoComprobanteId(long id);

    @Query(value = "select p from actOrden p "
            + "where p.invAlmacen.id =  ?1 ")
    List<ActOrden> findByInvAlmacenId(long id);

    @Query(value = "select p from actOrden p "
            + "where (:#{#paramBean.cnfLocal.id} = 0l or p.cnfLocal.id = :#{#paramBean.cnfLocal.id}) "
            //+ "and p.cnfLocal.cnfEmpresa.id = :#{#paramBean.cnfEmpresa.id} "
            + "and (p.flagIsventa = :#{#paramBean.flagIsventa}) "
            + "and (:#{#paramBean.invAlmacen.id} = 0l or p.invAlmacen.id = :#{#paramBean.invAlmacen.id}) "
            + "and (:#{#paramBean.cnfMaestro.id} = 0l or p.cnfMaestro.id = :#{#paramBean.cnfMaestro.id}) "
            + "and (p.fecha between :#{#paramBean.fechaDesde} and :#{#paramBean.fechaHasta}) "
            + "and (:#{#paramBean.flagEstado} = '-1' or p.flagEstado = :#{#paramBean.flagEstado}) "
            + "and p.cnfLocal.id in :#{#securityFilterDto.localIds}")
    List<ActOrden> getReportActOrden(@Param("paramBean") ParamBean paramBean, 
            @Param("securityFilterDto") SecurityFilterDto securityFilterDto);
    
    @Query(value = "select p from actOrden p "
            + "where p.cnfLocal.cnfEmpresa.id =  ?1 and month(fecha) = ?2 and flagEstado = '2' and flagIsventa = '1'")
    List<ActOrden> findByCnfEmpresaIdAndMonth(long id, int month);
    
}
