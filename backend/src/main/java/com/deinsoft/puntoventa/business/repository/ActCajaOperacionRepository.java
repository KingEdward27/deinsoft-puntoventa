package ocs.opensoft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.ActCajaOperacion;

public interface ActCajaOperacionRepository extends JpaRepository<ActCajaOperacion,Long> {
	@Query(value="select p from actCajaOperacion p "+ 
 "where upper(p.flag_ingreso) like %?1% and upper(p.estado) like %?2% ")

	List<ActCajaOperacion> getAllActCajaOperacion(String flagIngreso,String estado);
	@Query(value="select p from actCajaOperacion p "+
			"where p.actCajaTurno.id =  ?1 ")
	List<ActCajaOperacion>findByActCajaTurnoId(long id);

	@Query(value="select p from actCajaOperacion p "+
			"where p.actComprobante.id =  ?1 ")
	List<ActCajaOperacion>findByActComprobanteId(long id);

	@Query(value="select p from actCajaOperacion p "+
			"where p.actPago.id =  ?1 ")
	List<ActCajaOperacion>findByActPagoId(long id);


}
