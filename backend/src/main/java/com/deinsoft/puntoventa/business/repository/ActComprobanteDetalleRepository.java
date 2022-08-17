package ocs.opensoft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.ActComprobanteDetalle;

public interface ActComprobanteDetalleRepository extends JpaRepository<ActComprobanteDetalle,Long> {
	@Query(value="select p from actComprobanteDetalle p "+ 
 "where upper(p.descripcion) like %?1% ")

	List<ActComprobanteDetalle> getAllActComprobanteDetalle(String descripcion);
	@Query(value="select p from actComprobanteDetalle p "+
			"where p.actComprobante.id =  ?1 ")
	List<ActComprobanteDetalle>findByActComprobanteId(long id);

	@Query(value="select p from actComprobanteDetalle p "+
			"where p.cnfProducto.id =  ?1 ")
	List<ActComprobanteDetalle>findByCnfProductoId(long id);

	@Query(value="select p from actComprobanteDetalle p "+
			"where p.cnfImpuestoCondicion.id =  ?1 ")
	List<ActComprobanteDetalle>findByCnfImpuestoCondicionId(long id);


}
