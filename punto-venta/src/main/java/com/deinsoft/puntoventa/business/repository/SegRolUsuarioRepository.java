package com.deinsoft.puntoventa.business.repository;

import com.deinsoft.puntoventa.business.model.SegRolUsuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SegRolUsuarioRepository extends JpaRepository<SegRolUsuario, Long> {

    List<SegRolUsuario> findBySegUsuarioId(long id);
    
    List<SegRolUsuario> findBySegRolId(long id);
}
