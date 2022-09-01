package com.deinsoft.puntoventa.business.repository;

import com.deinsoft.puntoventa.business.bean.ParamBean;
import com.deinsoft.puntoventa.business.model.ActComprobante;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.InvAlmacenProducto;

public interface InvAlmacenProductoRepository extends JpaRepository<InvAlmacenProducto, Long> {

    @Query(value = "select p from invAlmacenProducto p ")

    List<InvAlmacenProducto> getAllInvAlmacenProducto();

    @Query(value = "select p from invAlmacenProducto p "
            + "where p.invAlmacen.id =  ?1 ")
    List<InvAlmacenProducto> findByInvAlmacenId(long id);

    @Query(value = "select p from invAlmacenProducto p "
            + "where p.cnfProducto.id =  ?1 ")
    List<InvAlmacenProducto> findByCnfProductoId(long id);

    @Query(value = "select p from invAlmacenProducto p "
            + "where p.cnfProducto.id =  ?1 and p.invAlmacen.id =  ?2")
    InvAlmacenProducto findByCnfProductoIdAndInvAlmacenId(long productoId,long almacenId);
    
    @Query(value = "select p from invAlmacenProducto p "
            + "where (:#{#paramBean.cnfLocal.id} = 0l or p.invAlmacen.cnfLocal.id = :#{#paramBean.cnfLocal.id}) "
            + "and (:#{#paramBean.invAlmacen.id} = 0l or p.invAlmacen.id = :#{#paramBean.invAlmacen.id}) ")
    List<InvAlmacenProducto> getReportInvAlmacen(@Param("paramBean") ParamBean paramBean);
}
