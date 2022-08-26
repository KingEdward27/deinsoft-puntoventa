package com.deinsoft.puntoventa.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.deinsoft.puntoventa.business.model.ActCaja;

public interface ActCajaRepository extends JpaRepository<ActCaja,Long> {
	@Query(value="select p from actCaja p "+ 
 "where upper(p.nombre) like %?1% and upper(p.estado) like %?2% ")

	List<ActCaja> getAllActCaja(String nombre,String estado);

}
