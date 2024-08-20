package com.deinsoft.puntoventa.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.ActContratoMov;

public interface ActContratoCorteRepository extends JpaRepository<ActContratoMov, Long> {

    @Query(value = "select p from actContratoMov p "
            + "where upper(p.flagEstado) like %?1% ")

    List<ActContratoMov> getAllActContratoCorte(String flagEstado);

    @Query(value = "select p from actContratoMov p "
            + "where p.actContrato.id =  ?1 ")
    List<ActContratoMov> findByActContratoId(long id);

    @Query(value = "select p from actContratoMov p "
            + "where p.segUsuario.id =  ?1 ")
    List<ActContratoMov> findBySegUsuarioId(long id);

}
