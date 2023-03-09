package com.deinsoft.puntoventa.business.repository;

import com.deinsoft.puntoventa.business.bean.ParamBean;
import com.deinsoft.puntoventa.business.model.ActCajaOperacion;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.ActPago;

public interface ActPagoRepository extends JpaRepository<ActPago,Long> {
	@Query(value="select p from actPago p where upper(p.serie) like %?1% and upper(p.numero) like %?2%")

	List<ActPago> getAllActPago(String serie, String numero);
        
//	@Query(value="select p from actPago p "+
//			"where p.actPagoProgramacion.id =  ?1 ")
//	List<ActPago>findByActPagoProgramacionId(long id);

        @Query(value = "select p from actPago p "
            + "where (p.fecha between :#{#paramBean.fechaDesde} and :#{#paramBean.fechaHasta})")
        List<ActPago> getReportActPago(@Param("paramBean") ParamBean paramBean);

}
