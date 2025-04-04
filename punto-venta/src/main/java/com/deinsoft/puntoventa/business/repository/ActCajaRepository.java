package com.deinsoft.puntoventa.business.repository;

import java.util.List;

import com.deinsoft.puntoventa.business.dto.SecurityFilterDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.deinsoft.puntoventa.business.model.ActCaja;
import com.deinsoft.puntoventa.business.model.ActCajaTurno;
import org.springframework.data.repository.query.Param;

public interface ActCajaRepository extends JpaRepository<ActCaja, Long> {

    @Query(value = "select p from actCaja p "
            + "where upper(p.nombre) like %?1% and upper(p.estado) like %?2% ")
    List<ActCaja> getAllActCaja(String nombre, String estado);

    @Query(value = "select p from actCaja p "
            + "where p.cnfEmpresa.id =  ?1 "
            + "and p.cnfLocal.id in :#{#securityFilterDto.localIds} ")
    List<ActCaja> findByCnfEmpresaId(long id,
                                     @Param("securityFilterDto") SecurityFilterDto securityFilterDto);

    @Query(value = "select p from actCaja p "
            + "where p.cnfEmpresa.id =  ?1 "
            + "and p.cnfLocal.id = ?2 ")
    List<ActCaja> findByCnfEmpresaIdAndLocalId(long id,Long localId);

}
