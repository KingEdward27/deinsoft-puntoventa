package com.deinsoft.puntoventa.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.SegRol;

public interface SegRolRepository extends JpaRepository<SegRol,Long> {
	@Query(value="select p from segRol p "+ 
 "where upper(p.nombre) like %?1% ")

	List<SegRol> getAllSegRol(String nombre);

}
