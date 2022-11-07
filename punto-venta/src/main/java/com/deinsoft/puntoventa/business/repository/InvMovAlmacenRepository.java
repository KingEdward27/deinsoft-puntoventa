package com.deinsoft.puntoventa.business.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.InvMovAlmacen;

public interface InvMovAlmacenRepository extends JpaRepository<InvMovAlmacen,Long> {
	@Query(value="select p from invMovAlmacen p "+ 
 "where upper(p.serie) like %?1% and upper(p.numero) like %?2% "
                + "and upper(p.numeroRef) like %?3% "
                + "and upper(p.observacion) like %?4% and upper(p.flagEstado) like %?5% ")

	List<InvMovAlmacen> getAllInvMovAlmacen(String serie,String numero,String numeroRef,
                String observacion,String flagEstado);
	@Query(value="select p from invMovAlmacen p "+
			"where p.invTipoMovAlmacen.id =  ?1 ")
	List<InvMovAlmacen>findByInvTipoMovAlmacenId(long id);

	@Query(value="select p from invMovAlmacen p "+
			"where p.cnfMaestro.id =  ?1 ")
	List<InvMovAlmacen>findByCnfMaestroId(long id);

	@Query(value="select p from invMovAlmacen p "+
			"where p.cnfLocal.id =  ?1 ")
	List<InvMovAlmacen>findByCnfLocalId(long id);

	@Query(value="select p from invMovAlmacen p "+
			"where p.cnfTipoComprobante.id =  ?1 ")
	List<InvMovAlmacen>findByCnfTipoComprobanteId(long id);

	@Query(value="select p from invMovAlmacen p "+
			"where p.invAlmacen.id =  ?1 ")
	List<InvMovAlmacen>findByInvAlmacenId(long id);


}
