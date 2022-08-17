package ocs.opensoft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.CnfNumComprobante;

public interface CnfNumComprobanteRepository extends JpaRepository<CnfNumComprobante,Long> {
	@Query(value="select p from cnfNumComprobante p "+ 
 "where upper(p.serie) like %?1% ")

	List<CnfNumComprobante> getAllCnfNumComprobante(String serie);
	@Query(value="select p from cnfNumComprobante p "+
			"where p.cnfTipoComprobante.id =  ?1 ")
	List<CnfNumComprobante>findByCnfTipoComprobanteId(long id);

	@Query(value="select p from cnfNumComprobante p "+
			"where p.cnfLocal.id =  ?1 ")
	List<CnfNumComprobante>findByCnfLocalId(long id);


}
