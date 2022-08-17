package ocs.opensoft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.ActComprobante;

public interface ActComprobanteRepository extends JpaRepository<ActComprobante,Long> {
	@Query(value="select p from actComprobante p "+ 
 "where upper(p.serie) like %?1% and upper(p.numero) like %?2% and upper(p.observacion) like %?3% and upper(p.flag_estado) like %?4% and upper(p.flag_isventa) like %?5% and upper(p.envio_pse_flag) like %?6% and upper(p.envio_pse_mensaje) like %?7% and upper(p.xmlhash) like %?8% and upper(p.codigoqr) like %?9% and upper(p.num_ticket) like %?10% ")

	List<ActComprobante> getAllActComprobante(long serie,String numero,String observacion,String flagEstado,String flagIsventa,String envioPseFlag,String envioPseMensaje,String xmlhash,String codigoqr,String numTicket);
	@Query(value="select p from actComprobante p "+
			"where p.actComprobante.id =  ?1 ")
	List<ActComprobante>findByActComprobanteId(long id);

	@Query(value="select p from actComprobante p "+
			"where p.cnfMaestro.id =  ?1 ")
	List<ActComprobante>findByCnfMaestroId(long id);

	@Query(value="select p from actComprobante p "+
			"where p.cnfFormaPago.id =  ?1 ")
	List<ActComprobante>findByCnfFormaPagoId(long id);

	@Query(value="select p from actComprobante p "+
			"where p.cnfMoneda.id =  ?1 ")
	List<ActComprobante>findByCnfMonedaId(long id);

	@Query(value="select p from actComprobante p "+
			"where p.cnfLocal.id =  ?1 ")
	List<ActComprobante>findByCnfLocalId(long id);

	@Query(value="select p from actComprobante p "+
			"where p.cnfTipoComprobante.id =  ?1 ")
	List<ActComprobante>findByCnfTipoComprobanteId(long id);

	@Query(value="select p from actComprobante p "+
			"where p.invAlmacen.id =  ?1 ")
	List<ActComprobante>findByInvAlmacenId(long id);


}
