package ocs.opensoft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.CnfMarca;

public interface CnfMarcaRepository extends JpaRepository<CnfMarca,Long> {
	@Query(value="select p from cnfMarca p "+ 
 "where upper(p.nombre) like %?1% and upper(p.flag_estado) like %?2% ")

	List<CnfMarca> getAllCnfMarca(String nombre,String flagEstado);
	@Query(value="select p from cnfMarca p "+
			"where p.cnfEmpresa.id =  ?1 ")
	List<CnfMarca>findByCnfEmpresaId(long id);


}
