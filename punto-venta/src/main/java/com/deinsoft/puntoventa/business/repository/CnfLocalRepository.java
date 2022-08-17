package com.deinsoft.puntoventa.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.CnfLocal;

public interface CnfLocalRepository extends JpaRepository<CnfLocal,Long> {
	@Query(value="select p from cnfLocal p "+ 
 "where upper(p.nombre) like %?1% and upper(p.direccion) like %?2% ")

	List<CnfLocal> getAllCnfLocal(String nombre,String direccion);
	@Query(value="select p from cnfLocal p "+
			"where p.cnfEmpresa.id =  ?1 ")
	List<CnfLocal>findByCnfEmpresaId(long id);


}
