package ocs.opensoft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.CnfRegion;

public interface CnfRegionRepository extends JpaRepository<CnfRegion,Long> {
	@Query(value="select p from cnfRegion p "+ 
 "where upper(p.nombre) like %?1% ")

	List<CnfRegion> getAllCnfRegion(String nombre);
	@Query(value="select p from cnfRegion p "+
			"where p.cnfPais.id =  ?1 ")
	List<CnfRegion>findByCnfPaisId(long id);


}
