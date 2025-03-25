package com.deinsoft.puntoventa.business.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deinsoft.puntoventa.business.commons.service.CommonService;

import com.deinsoft.puntoventa.business.model.CnfTipoComprobante;

@Service
@Transactional
public interface CnfTipoComprobanteService extends CommonService<CnfTipoComprobante> {

    public List<CnfTipoComprobante> getAllCnfTipoComprobante(CnfTipoComprobante cnfTipoComprobante);

    public CnfTipoComprobante getCnfTipoComprobante(Long id);

    public CnfTipoComprobante saveCnfTipoComprobante(CnfTipoComprobante cnfTipoComprobante);

    public List<CnfTipoComprobante> getAllCnfTipoComprobante();

    public List<CnfTipoComprobante> getAllCnfTipoComprobanteVentas();

    public List<CnfTipoComprobante> getAllCnfTipoComprobanteMovAlmacen();

    public List<CnfTipoComprobante> getAllCnfTipoComprobanteContrato();

    public void delete(long id);
}
