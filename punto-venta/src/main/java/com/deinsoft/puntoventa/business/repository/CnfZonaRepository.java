package com.deinsoft.puntoventa.business.repository;

import com.deinsoft.puntoventa.business.model.CnfZona;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CnfZonaRepository extends JpaRepository<CnfZona, Long> {

    @Query(value = "select p from cnfZona p "
            + "where upper(p.nombre) like %?1% ")
    List<CnfZona> getAllCnfZona(String nombre);

    @Query(value = "select p from cnfZona p "
            + "where p.cnfEmpresa.id =  ?1 ")
    List<CnfZona> findByCnfEmpresaId(long id);

}
