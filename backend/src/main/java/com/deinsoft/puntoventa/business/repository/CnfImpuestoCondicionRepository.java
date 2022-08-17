package ocs.opensoft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.CnfImpuestoCondicion;

public interface CnfImpuestoCondicionRepository extends JpaRepository<CnfImpuestoCondicion,Long> {
	@Query(value="select p from cnfImpuestoCondicion p "+ 
 "where upper(p.codigo_sunat) like %?1% and upper(p.nombre) like %?2% ")

	List<CnfImpuestoCondicion> getAllCnfImpuestoCondicion(String codigoSunat,String nombre);

}
