package ocs.opensoft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.InvAlmacenProducto;

public interface InvAlmacenProductoRepository extends JpaRepository<InvAlmacenProducto,Long> {
	@Query(value="select p from invAlmacenProducto p ")

	List<InvAlmacenProducto> getAllInvAlmacenProducto();
	@Query(value="select p from invAlmacenProducto p "+
			"where p.invAlmacen.id =  ?1 ")
	List<InvAlmacenProducto>findByInvAlmacenId(long id);

	@Query(value="select p from invAlmacenProducto p "+
			"where p.cnfProducto.id =  ?1 ")
	List<InvAlmacenProducto>findByCnfProductoId(long id);


}
