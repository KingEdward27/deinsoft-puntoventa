package com.deinsoft.puntoventa.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.CnfDistrito;

public interface CnfDistritoRepository extends JpaRepository<CnfDistrito,Long> {
	@Query(value="select p from cnfDistrito p "+ 
 "where upper(p.nombre) like %?1% ")

	List<CnfDistrito> getAllCnfDistrito(String nombre);
	@Query(value="select p from cnfDistrito p "+
			"where p.cnfProvincia.id =  ?1 ")
	List<CnfDistrito>findByCnfProvinciaId(long id);


}
