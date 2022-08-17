package com.deinsoft.puntoventa.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.CnfSubCategoria;

public interface CnfSubCategoriaRepository extends JpaRepository<CnfSubCategoria,Long> {
	@Query(value="select p from cnfSubCategoria p "+ 
 "where upper(p.nombre) like %?1% and upper(p.flagEstado) like %?2% ")

	List<CnfSubCategoria> getAllCnfSubCategoria(String nombre,String flagEstado);
	@Query(value="select p from cnfSubCategoria p "+
			"where p.cnfCategoria.id =  ?1 ")
	List<CnfSubCategoria>findByCnfCategoriaId(long id);

	@Query(value="select p from cnfSubCategoria p "+
			"where p.cnfEmpresa.id =  ?1 ")
	List<CnfSubCategoria>findByCnfEmpresaId(long id);


}
