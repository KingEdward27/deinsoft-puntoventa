package ocs.opensoft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.ActPago;

public interface ActPagoRepository extends JpaRepository<ActPago,Long> {
	@Query(value="select p from actPago p "+ 
 "where upper(p.serie) like %?1% and upper(p.numero) like %?2% ")

	List<ActPago> getAllActPago(long serie,String numero);
	@Query(value="select p from actPago p "+
			"where p.cnfTipoComprobante.id =  ?1 ")
	List<ActPago>findByCnfTipoComprobanteId(long id);


}
