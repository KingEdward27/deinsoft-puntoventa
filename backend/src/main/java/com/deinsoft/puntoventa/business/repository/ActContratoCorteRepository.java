package ocs.opensoft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.ActContratoCorte;

public interface ActContratoCorteRepository extends JpaRepository<ActContratoCorte,Long> {
	@Query(value="select p from actContratoCorte p "+ 
 "where upper(p.flg_estado) like %?1% ")

	List<ActContratoCorte> getAllActContratoCorte(String flgEstado);
	@Query(value="select p from actContratoCorte p "+
			"where p.actContrato.id =  ?1 ")
	List<ActContratoCorte>findByActContratoId(long id);

	@Query(value="select p from actContratoCorte p "+
			"where p.segUsuario.id =  ?1 ")
	List<ActContratoCorte>findBySegUsuarioId(long id);


}
