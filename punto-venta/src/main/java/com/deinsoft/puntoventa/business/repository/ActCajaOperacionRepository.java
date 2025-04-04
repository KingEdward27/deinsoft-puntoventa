package com.deinsoft.puntoventa.business.repository;

import com.deinsoft.puntoventa.business.bean.ParamBean;
import com.deinsoft.puntoventa.business.dto.SecurityFilterDto;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.deinsoft.puntoventa.business.model.ActCajaOperacion;
import com.deinsoft.puntoventa.business.model.ActPago;
import org.springframework.data.repository.query.Param;

public interface ActCajaOperacionRepository extends JpaRepository<ActCajaOperacion, Long> {

    @Query(value = "select p from actCajaOperacion p "
            + "where upper(p.estado) like %?1% "
            + "and p.actCajaTurno.actCaja.cnfEmpresa.id = :#{#securityFilterDto.empresaId} "
            + "and p.actCajaTurno.actCaja.cnfLocal.id in :#{#securityFilterDto.localIds}")
    List<ActCajaOperacion> getAllActCajaOperacion(String estado,@Param("securityFilterDto") SecurityFilterDto securityFilterDto);

    @Query(value = "select p from actCajaOperacion p "
            + "where p.actCajaTurno.id =  ?1 ")
    List<ActCajaOperacion> findByActCajaTurnoId(long id);

    @Query(value = "select p from actCajaOperacion p "
            + "where p.actComprobante.id =  ?1 ")
    List<ActCajaOperacion> findByActComprobanteId(long id);

    @Query(value = "select p from actCajaOperacion p "
            + "where p.actPago.id =  ?1 ")
    List<ActCajaOperacion> findByActPagoId(long id);

    @Query(value = "select p from actCajaOperacion p "
            + "where (p.fecha between :#{#paramBean.fechaDesde} and :#{#paramBean.fechaHasta}) "
            + "and (:#{#paramBean.cnfLocal.id} = 0l or p.actCajaTurno.actCaja.cnfLocal.id = :#{#paramBean.cnfLocal.id}) "
            + "and p.actCajaTurno.actCaja.cnfEmpresa.id = :#{#securityFilterDto.empresaId} "
            + "and p.actCajaTurno.actCaja.cnfLocal.id in :#{#securityFilterDto.localIds}")
    List<ActCajaOperacion> getReportActCajaOperacion(@Param("paramBean") ParamBean paramBean,@Param("securityFilterDto") SecurityFilterDto securityFilterDto);
    
    void deleteByActPago(ActPago actPago);
}
