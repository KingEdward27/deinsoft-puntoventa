package com.deinsoft.puntoventa.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.CnfTipoDocumento;

public interface CnfTipoDocumentoRepository extends JpaRepository<CnfTipoDocumento,Long> {
	@Query(value="select p from cnfTipoDocumento p "+ 
 "where upper(p.abreviatura) like %?1% and upper(p.nombre) like %?2% "
                + "and upper(p.codigoSunat) like %?3% and upper(p.flagEstado) like %?4% ")

	List<CnfTipoDocumento> getAllCnfTipoDocumento(String abreviatura,String nombre,String codigoSunat,String flagEstado);

}
