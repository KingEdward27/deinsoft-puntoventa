package ocs.opensoft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.CnfUnidadMedida;

public interface CnfUnidadMedidaRepository extends JpaRepository<CnfUnidadMedida,Long> {
	@Query(value="select p from cnfUnidadMedida p "+ 
 "where upper(p.codigo_sunat) like %?1% and upper(p.nombre) like %?2% and upper(p.flag_estado) like %?3% ")

	List<CnfUnidadMedida> getAllCnfUnidadMedida(String codigoSunat,String nombre,String flagEstado);

}
