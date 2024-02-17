package ocs.opensoft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.CnfTipoSistema;

public interface CnfTipoSistemaRepository extends JpaRepository<CnfTipoSistema,Long> {
	@Query(value="select p from cnfTipoSistema p "+ 
 "where upper(p.nombre) like %?1% ")

	List<CnfTipoSistema> getAllCnfTipoSistema(String nombre);

}
