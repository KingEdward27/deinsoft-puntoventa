package com.deinsoft.puntoventa.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.CnfProvincia;

public interface CnfProvinciaRepository extends JpaRepository<CnfProvincia,Long> {
	@Query(value="select p from cnfProvincia p "+ 
 "where upper(p.nombre) like %?1% ")

	List<CnfProvincia> getAllCnfProvincia(String nombre);
	@Query(value="select p from cnfProvincia p "+
			"where p.cnfRegion.id =  ?1 ")
	List<CnfProvincia>findByCnfRegionId(long id);


}
