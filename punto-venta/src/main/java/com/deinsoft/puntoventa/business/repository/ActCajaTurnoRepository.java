package com.deinsoft.puntoventa.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.deinsoft.puntoventa.business.model.ActCajaTurno;

public interface ActCajaTurnoRepository extends JpaRepository<ActCajaTurno,Long> {
	@Query(value="select p from actCajaTurno p "+ 
 "where upper(p.estado) like %?1% ")

	List<ActCajaTurno> getAllActCajaTurno(String estado);
	@Query(value="select p from actCajaTurno p "+
			"where p.segUsuario.id =  ?1 ")
	List<ActCajaTurno>findBySegUsuarioId(long id);


}
