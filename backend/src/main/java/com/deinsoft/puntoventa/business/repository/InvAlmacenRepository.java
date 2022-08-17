package ocs.opensoft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.InvAlmacen;

public interface InvAlmacenRepository extends JpaRepository<InvAlmacen,Long> {
	@Query(value="select p from invAlmacen p "+ 
 "where upper(p.nombre) like %?1% and upper(p.flag_estado) like %?2% ")

	List<InvAlmacen> getAllInvAlmacen(String nombre,String flagEstado);
	@Query(value="select p from invAlmacen p "+
			"where p.cnfLocal.id =  ?1 ")
	List<InvAlmacen>findByCnfLocalId(long id);


}
