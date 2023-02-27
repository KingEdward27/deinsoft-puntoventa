package com.deinsoft.puntoventa.business.repository;

import com.deinsoft.puntoventa.business.model.ActComprobante;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.ActPagoProgramacion;
import java.time.LocalDate;

public interface ActPagoProgramacionRepository extends JpaRepository<ActPagoProgramacion,Long> {
	@Query(value="select p from actPagoProgramacion p ")
	List<ActPagoProgramacion> getAllActPagoProgramacion();
        
	@Query(value="select p from actPagoProgramacion p "+
			"where p.actComprobante.id =  ?1 ")
	List<ActPagoProgramacion>findByActComprobanteId(long id);

        @Query(value="select p from actPagoProgramacion p "
                + "left join p.actComprobante left join p.actContrato "+
			"where (:id = 0l or (p.actComprobante != null and p.actComprobante.cnfMaestro.id = :id) "
                + "or (p.actContrato != null and p.actContrato.cnfMaestro.id = :id)) "
                + "and (:fechaVencimiento = null or p.fechaVencimiento <= :fechaVencimiento) order by p.id")
	List<ActPagoProgramacion>findByCnfMaestroId(@Param("id") long id,@Param("fechaVencimiento") LocalDate fechaVencimiento);
        
        void deleteByActComprobante(ActComprobante actComprobante);
}
