package com.deinsoft.puntoventa.business.repository;

import com.deinsoft.puntoventa.business.model.InvMovAlmacen;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.InvMovAlmacenDet;

public interface InvMovAlmacenDetRepository extends JpaRepository<InvMovAlmacenDet, Long> {

    @Query(value = "select p from invMovAlmacenDet p "
            + "where upper(p.nroserie) like %?1% ")

    List<InvMovAlmacenDet> getAllInvMovAlmacenDet(String nroserie);

    @Query(value = "select p from invMovAlmacenDet p "
            + "where p.invMovAlmacen.id =  ?1 ")
    List<InvMovAlmacenDet> findByInvMovAlmacenId(long id);

    @Query(value = "select p from invMovAlmacenDet p "
            + "where p.cnfProducto.id =  ?1 ")
    List<InvMovAlmacenDet> findByCnfProductoId(long id);

    void deleteByInvMovAlmacen(InvMovAlmacen invMovAlmacen);
}
