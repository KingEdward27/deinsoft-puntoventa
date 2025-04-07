package com.deinsoft.puntoventa.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.CnfMaestro;
import com.deinsoft.puntoventa.business.model.CnfProducto;

public interface CnfMaestroRepository extends JpaRepository<CnfMaestro, Long> {

    @Query(value = "select p from cnfMaestro p "
            + "where upper(p.nroDoc) like %?1% and upper(p.nombres) like %?2% and upper(p.apellidoPaterno) like %?3% "
            + "and upper(p.apellidoMaterno) like %?4% and upper(p.razonSocial) like %?5% "
            + "and upper(p.direccion) like %?6% and upper(p.correo) like %?7% "
            + "and upper(p.telefono) like %?8% and upper(p.flagEstado) like %?9% ")

    List<CnfMaestro> getAllCnfMaestro(String nroDoc, String nombres, String apellidoPaterno, String apellidoMaterno, String razonSocial, String direccion, String correo, String telefono, String flagEstado);

    @Query(value = "select p from cnfMaestro p "
            + "where p.cnfTipoDocumento.id =  ?1 ")
    List<CnfMaestro> findByCnfTipoDocumentoId(long id);

    @Query(value = "select p from cnfMaestro p "
            + "where p.cnfEmpresa.id =  ?1 ")
    List<CnfMaestro> findByCnfEmpresaId(long id);

    @Query(value = "select p from cnfMaestro p "
            + "where p.cnfDistrito.id =  ?1 ")
    List<CnfMaestro> findByCnfDistritoId(long id);

    @Query(value = "select p from cnfMaestro p "
            + "where (upper(p.razonSocial) "
            + "like CONCAT('%',:nombre,'%') "
            + "or upper(p.nroDoc) like CONCAT('%',:nombre,'%')) "
    )
    List<CnfMaestro> getAllCnfMestroTypeHead(@Param("nombre") String nombre);
    
    CnfMaestro findByNroDocAndCnfEmpresaId(String nroDoc,Long empresaId);
}
