package com.deinsoft.puntoventa.business.repository;

import com.deinsoft.puntoventa.business.bean.ParamBean;
import com.deinsoft.puntoventa.business.model.ActComprobante;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.InvMovimientoProducto;

public interface InvMovimientoProductoRepository extends JpaRepository<InvMovimientoProducto, Long> {

    @Query(value = "select p from invMovimientoProducto p ")

    List<InvMovimientoProducto> getAllInvMovimientoProducto();

    @Query(value = "select p from invMovimientoProducto p "
            + "where p.invAlmacen.id =  ?1 ")
    List<InvMovimientoProducto> findByInvAlmacenId(long id);

    @Query(value = "select p from invMovimientoProducto p "
            + "where p.cnfProducto.id =  ?1 ")
    List<InvMovimientoProducto> findByCnfProductoId(long id);

    @Query(value = "select p from invMovimientoProducto p "
            + "where p.actComprobante.id =  ?1 ")
    List<InvMovimientoProducto> findByActComprobanteId(long id);

    @Query(value = "select p from invMovimientoProducto p "
            + "where (:#{#paramBean.cnfLocal.id} = 0l or p.invAlmacen.cnfLocal.id = :#{#paramBean.cnfLocal.id}) "
            + "and (:#{#paramBean.invAlmacen.id} = 0l or p.invAlmacen.id = :#{#paramBean.invAlmacen.id}) "
            + "and (p.fecha between :#{#paramBean.fechaDesde} and :#{#paramBean.fechaHasta})")
    List<InvMovimientoProducto> getReportInvMovimientoProducto(@Param("paramBean") ParamBean paramBean);
    
    @Query(value = "select p from invMovimientoProducto p "
            + "where (:#{#paramBean.cnfLocal.id} = 0l or p.invAlmacen.cnfLocal.id = :#{#paramBean.cnfLocal.id}) "
            + "and (:#{#paramBean.invAlmacen.id} = 0l or p.invAlmacen.id = :#{#paramBean.invAlmacen.id}) "
            + "and (p.fecha < :#{#paramBean.fechaDesde})")
    List<InvMovimientoProducto> getSaldoReportInvMovimientoProducto(@Param("paramBean") ParamBean paramBean);
    
    void deleteByActComprobante(ActComprobante actComprobante);
}
