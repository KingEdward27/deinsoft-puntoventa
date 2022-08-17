package ocs.opensoft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.CnfMoneda;

public interface CnfMonedaRepository extends JpaRepository<CnfMoneda,Long> {
	@Query(value="select p from cnfMoneda p "+ 
 "where upper(p.codigo) like %?1% and upper(p.nombre) like %?2% and upper(p.flag_estado) like %?3% ")

	List<CnfMoneda> getAllCnfMoneda(String codigo,String nombre,String flagEstado);

}
