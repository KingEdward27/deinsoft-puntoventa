package com.deinsoft.puntoventa.business.repository;

import com.deinsoft.puntoventa.business.bean.ParamBean;
import com.deinsoft.puntoventa.business.dto.SecurityFilterDto;
import com.deinsoft.puntoventa.business.model.ActCajaOperacion;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.deinsoft.puntoventa.business.model.ActCajaTurno;
import org.springframework.data.repository.query.Param;

public interface ActCajaTurnoRepository extends JpaRepository<ActCajaTurno, Long> {

    @Query(value = "select p from actCajaTurno p "
            + "where upper(p.estado) like %?1% "
            + "and p.actCaja.cnfEmpresa.id = :#{#securityFilterDto.empresaId}")
    List<ActCajaTurno> getAllActCajaTurno(String estado,@Param("securityFilterDto") SecurityFilterDto securityFilterDto);

    @Query(value = "select p from actCajaTurno p "
            + "where p.segUsuario.id = :id "
            + "and p.actCaja.cnfEmpresa.id = :#{#securityFilterDto.empresaId} "
            +"order by p.id desc")
    List<ActCajaTurno> findBySegUsuarioId(@Param("id") long id,@Param("securityFilterDto") SecurityFilterDto securityFilterDto);


    @Query(value = "select p from actCajaTurno p "
            + "where ("
            + "(DATE(p.fechaApertura) between DATE(:#{#paramBean.fechaDesde}) and DATE(:#{#paramBean.fechaHasta})) "
            + "or (p.fechaCierre is null or "
            + "(p.fechaCierre is not null and DATE(p.fechaCierre) "
            + "between DATE(:#{#paramBean.fechaDesde}) and DATE(:#{#paramBean.fechaHasta})))) "
            + "and p.actCaja.cnfEmpresa.id = :#{#paramBean.cnfLocal.cnfEmpresa.id}")
    List<ActCajaTurno> getReportActCajaTurno(@Param("paramBean") ParamBean paramBean);
    
}
