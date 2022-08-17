package ocs.opensoft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.CnfEmpresa;

public interface CnfEmpresaRepository extends JpaRepository<CnfEmpresa,Long> {
	@Query(value="select p from cnfEmpresa p "+ 
 "where upper(p.nombre) like %?1% and upper(p.descripcion) like %?2% and upper(p.nro_documento) like %?3% and upper(p.direccion) like %?4% and upper(p.telefono) like %?5% and upper(p.estado) like %?6% and upper(p.token) like %?7% ")

	List<CnfEmpresa> getAllCnfEmpresa(String nombre,String descripcion,String nroDocumento,String direccion,String telefono,String estado,String token);
	@Query(value="select p from cnfEmpresa p "+
			"where p.cnfTipoDocumento.id =  ?1 ")
	List<CnfEmpresa>findByCnfTipoDocumentoId(long id);

	@Query(value="select p from cnfEmpresa p "+
			"where p.cnfDistrito.id =  ?1 ")
	List<CnfEmpresa>findByCnfDistritoId(long id);


}
