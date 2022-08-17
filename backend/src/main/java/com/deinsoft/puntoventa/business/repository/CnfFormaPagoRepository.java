package ocs.opensoft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.CnfFormaPago;

public interface CnfFormaPagoRepository extends JpaRepository<CnfFormaPago,Long> {
	@Query(value="select p from cnfFormaPago p "+ 
 "where upper(p.nombre) like %?1% and upper(p.flag_estado) like %?2% ")

	List<CnfFormaPago> getAllCnfFormaPago(String nombre,String flagEstado);
	@Query(value="select p from cnfFormaPago p "+
			"where p.cnfEmpresa.id =  ?1 ")
	List<CnfFormaPago>findByCnfEmpresaId(long id);


}
