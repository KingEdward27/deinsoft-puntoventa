package ocs.opensoft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.CnfProducto;

public interface CnfProductoRepository extends JpaRepository<CnfProducto,Long> {
	@Query(value="select p from cnfProducto p "+ 
 "where upper(p.codigo) like %?1% and upper(p.nombre) like %?2% and upper(p.ruta_imagen) like %?3% and upper(p.flag_estado) like %?4% and upper(p.barcode) like %?5% ")

	List<CnfProducto> getAllCnfProducto(String codigo,String nombre,String rutaImagen,String flagEstado,String barcode);
	@Query(value="select p from cnfProducto p "+
			"where p.cnfUnidadMedida.id =  ?1 ")
	List<CnfProducto>findByCnfUnidadMedidaId(long id);

	@Query(value="select p from cnfProducto p "+
			"where p.cnfEmpresa.id =  ?1 ")
	List<CnfProducto>findByCnfEmpresaId(long id);

	@Query(value="select p from cnfProducto p "+
			"where p.cnfSubCategoria.id =  ?1 ")
	List<CnfProducto>findByCnfSubCategoriaId(long id);

	@Query(value="select p from cnfProducto p "+
			"where p.cnfMarca.id =  ?1 ")
	List<CnfProducto>findByCnfMarcaId(long id);


}
