package com.deinsoft.puntoventa.business.repository;

import com.deinsoft.puntoventa.business.bean.ParamBean;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.ActComprobante;
import com.deinsoft.puntoventa.business.model.CnfLocal;

public interface ActComprobanteRepository extends JpaRepository<ActComprobante, Long> {

    @Query(value = "select p from actComprobante p "
            + "where upper(p.serie) like %?1% and upper(p.numero) like %?2% and upper(p.observacion) like %?3% "
            + "and upper(p.flagEstado) like %?4% and upper(p.flagIsventa) "
            + "like %?5% and upper(p.envioPseFlag) like %?6% and upper(p.envioPseMensaje) like %?7% "
            + "and upper(p.xmlhash) like %?8% and upper(p.codigoqr) like %?9% and upper(p.numTicket) like %?10% ")
    List<ActComprobante> getAllActComprobante(String serie, String numero, 
                                                String observacion, String flagEstado, 
                                                String flagIsventa, String envioPseFlag, String envioPseMensaje, 
                                                String xmlhash, String codigoqr, String numTicket);

    @Query(value = "select p from actComprobante p "
            + "where p.cnfMaestro.id =  ?1 ")
    List<ActComprobante> findByCnfMaestroId(long id);

    @Query(value = "select p from actComprobante p "
            + "where p.cnfFormaPago.id =  ?1 ")
    List<ActComprobante> findByCnfFormaPagoId(long id);

    @Query(value = "select p from actComprobante p "
            + "where p.cnfMoneda.id =  ?1 ")
    List<ActComprobante> findByCnfMonedaId(long id);

    @Query(value = "select p from actComprobante p "
            + "where p.cnfLocal.id =  ?1 ")
    List<ActComprobante> findByCnfLocalId(long id);

    @Query(value = "select p from actComprobante p "
            + "where p.cnfTipoComprobante.id =  ?1 ")
    List<ActComprobante> findByCnfTipoComprobanteId(long id);

    @Query(value = "select p from actComprobante p "
            + "where p.invAlmacen.id =  ?1 ")
    List<ActComprobante> findByInvAlmacenId(long id);

    @Query(value = "select p from actComprobante p "
            + "where (:#{#paramBean.cnfLocal.id} = 0l or p.cnfLocal.id = :#{#paramBean.cnfLocal.id}) "
            + "and (p.flagIsventa = :#{#paramBean.flagIsventa}) "
            + "and (:#{#paramBean.invAlmacen.id} = 0l or p.invAlmacen.id = :#{#paramBean.invAlmacen.id}) "
            + "and (:#{#paramBean.cnfMaestro.id} = 0l or p.cnfMaestro.id = :#{#paramBean.cnfMaestro.id}) "
            + "and (p.fecha between :#{#paramBean.fechaDesde} and :#{#paramBean.fechaHasta})")
    List<ActComprobante> getReportActComprobante(@Param("paramBean") ParamBean paramBean);

}
