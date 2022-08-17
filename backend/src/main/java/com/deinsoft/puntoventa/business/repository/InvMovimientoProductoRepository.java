package ocs.opensoft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.InvMovimientoProducto;

public interface InvMovimientoProductoRepository extends JpaRepository<InvMovimientoProducto,Long> {
	@Query(value="select p from invMovimientoProducto p ")

	List<InvMovimientoProducto> getAllInvMovimientoProducto();
	@Query(value="select p from invMovimientoProducto p "+
			"where p.invAlmacen.id =  ?1 ")
	List<InvMovimientoProducto>findByInvAlmacenId(long id);

	@Query(value="select p from invMovimientoProducto p "+
			"where p.cnfProducto.id =  ?1 ")
	List<InvMovimientoProducto>findByCnfProductoId(long id);

	@Query(value="select p from invMovimientoProducto p "+
			"where p.actComprobante.id =  ?1 ")
	List<InvMovimientoProducto>findByActComprobanteId(long id);


}
