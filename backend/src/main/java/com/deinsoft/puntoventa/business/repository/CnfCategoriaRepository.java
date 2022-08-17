package ocs.opensoft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.CnfCategoria;

public interface CnfCategoriaRepository extends JpaRepository<CnfCategoria,Long> {
	@Query(value="select p from cnfCategoria p "+ 
 "where upper(p.nombre) like %?1% and upper(p.flag_estado) like %?2% ")

	List<CnfCategoria> getAllCnfCategoria(String nombre,String flagEstado);
	@Query(value="select p from cnfCategoria p "+
			"where p.cnfEmpresa.id =  ?1 ")
	List<CnfCategoria>findByCnfEmpresaId(long id);


}
