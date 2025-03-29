package com.deinsoft.puntoventa.business.repository;

import com.deinsoft.puntoventa.business.model.CnfPaqueteProducto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CnfPaqueteProductoRepository extends JpaRepository<CnfPaqueteProducto, UUID> {
    
}
