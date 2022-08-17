package com.deinsoft.puntoventa.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.CnfPais;

public interface CnfPaisRepository extends JpaRepository<CnfPais,Long> {
	@Query(value="select p from cnfPais p "+ 
 "where upper(p.nombre) like %?1% and upper(p.isocode) like %?2% ")

	List<CnfPais> getAllCnfPais(String nombre,String isocode);

}
