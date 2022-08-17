package com.deinsoft.puntoventa.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.CnfTipoComprobante;

public interface CnfTipoComprobanteRepository extends JpaRepository<CnfTipoComprobante,Long> {
	@Query(value="select p from cnfTipoComprobante p "+ 
 "where upper(p.nombre) like %?1% "
                    + "and upper(p.codigoSunat) like %?2% and upper(p.codigo) like %?3% "
                + "and upper(p.flagElectronico) like %?4% ")

	List<CnfTipoComprobante> getAllCnfTipoComprobante(String nombre,String codigoSunat,String codigo,String flagElectronico);

}
