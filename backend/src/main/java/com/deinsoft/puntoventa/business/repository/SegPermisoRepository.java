package ocs.opensoft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.SegPermiso;

public interface SegPermisoRepository extends JpaRepository<SegPermiso,Long> {
	@Query(value="select p from segPermiso p ")

	List<SegPermiso> getAllSegPermiso();
	@Query(value="select p from segPermiso p "+
			"where p.segRol.id =  ?1 ")
	List<SegPermiso>findBySegRolId(long id);

	@Query(value="select p from segPermiso p "+
			"where p.segMenu.id =  ?1 ")
	List<SegPermiso>findBySegMenuId(long id);

	@Query(value="select p from segPermiso p "+
			"where p.segAccion.id =  ?1 ")
	List<SegPermiso>findBySegAccionId(long id);


}
