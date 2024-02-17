package ocs.opensoft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.CnfMenuTipoSistema;

public interface CnfMenuTipoSistemaRepository extends JpaRepository<CnfMenuTipoSistema,Long> {
	@Query(value="select p from cnfMenuTipoSistema p ")

	List<CnfMenuTipoSistema> getAllCnfMenuTipoSistema();
	@Query(value="select p from cnfMenuTipoSistema p "+
			"where p.segMenu.id =  ?1 ")
	List<CnfMenuTipoSistema>findBySegMenuId(long id);


}
