package ocs.opensoft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.SegAccion;

public interface SegAccionRepository extends JpaRepository<SegAccion,Long> {
	@Query(value="select p from segAccion p "+ 
 "where upper(p.nombre) like %?1% and upper(p.descripcion) like %?2% ")

	List<SegAccion> getAllSegAccion(String nombre,String descripcion);

}
