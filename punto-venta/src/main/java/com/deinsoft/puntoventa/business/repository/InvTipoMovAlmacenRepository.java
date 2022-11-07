package com.deinsoft.puntoventa.business.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.InvTipoMovAlmacen;

public interface InvTipoMovAlmacenRepository extends JpaRepository<InvTipoMovAlmacen, Long> {

    @Query(value = "select p from invTipoMovAlmacen p "
            + "where upper(p.nombre) like %?1% and upper(p.codigoSunat) like %?2% "
            + "and upper(p.naturaleza) like %?3% ")
    List<InvTipoMovAlmacen> getAllInvTipoMovAlmacen(String nombre, String codigoSunat, String naturaleza);

}
