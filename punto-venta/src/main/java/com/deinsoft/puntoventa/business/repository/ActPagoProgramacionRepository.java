package com.deinsoft.puntoventa.business.repository;

import com.deinsoft.puntoventa.business.bean.ParamBean;
import com.deinsoft.puntoventa.business.dto.SecurityFilterDto;
import com.deinsoft.puntoventa.business.model.ActCajaOperacion;
import com.deinsoft.puntoventa.business.model.ActComprobante;
import com.deinsoft.puntoventa.business.model.ActContrato;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.ActPagoProgramacion;
import java.time.LocalDate;

public interface ActPagoProgramacionRepository extends JpaRepository<ActPagoProgramacion, Long> {

    @Query(value = "select p from actPagoProgramacion p "
            + "where ((p.actComprobante != null and p.actComprobante.cnfLocal.id in :#{#securityFilterDto.localIds}) "
            + "     or (p.actContrato.cnfLocal.id in :#{#securityFilterDto.localIds}))")
    List<ActPagoProgramacion> getAllActPagoProgramacion(@Param("securityFilterDto") SecurityFilterDto securityFilterDto);

    @Query(value = "select p from actPagoProgramacion p "
            + "where p.actComprobante.id =  ?1 ")
    List<ActPagoProgramacion> findByActComprobanteId(long id);

    @Query(value = "select p from actPagoProgramacion p "
            + "where p.actContrato.id =  ?1 order by p.id desc")
    List<ActPagoProgramacion> findByActContratoId(long id);
    
    @Query(value = "select p from actPagoProgramacion p "
            + "left join p.actComprobante left join p.actContrato "
            + "where (:id = 0l or (p.actComprobante != null and p.actComprobante.cnfMaestro.id = :id) "
            + "or (p.actContrato != null and p.actContrato.cnfMaestro.id = :id)) "
            + "and ((p.actComprobante != null and p.actComprobante.cnfLocal.id in :#{#securityFilterDto.localIds}) "
            + "         or (p.actContrato.cnfLocal.id in :#{#securityFilterDto.localIds})) "
            + "and (:fechaVencimiento = null or p.fechaVencimiento <= :fechaVencimiento) order by p.id")
    List<ActPagoProgramacion> findByCnfMaestroId(@Param("id") long id, @Param("fechaVencimiento") LocalDate fechaVencimiento
            ,@Param("securityFilterDto") SecurityFilterDto securityFilterDto);

    void deleteByActComprobante(ActComprobante actComprobante);

    void deleteByActContrato(ActContrato actContrato);

    @Query("DELETE FROM actPagoProgramacion p WHERE monto = montoPendiente")
    Integer deleteByCorte();
}
