package com.deinsoft.puntoventa.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.SegUsuario;

public interface SegUsuarioRepository extends JpaRepository<SegUsuario, Long> {

    @Query(value = "select p from segUsuario p "
            + "where upper(p.nombre) like %?1% and upper(p.email) like %?2% and upper(p.password) like %?3% ")
    List<SegUsuario> getAllSegUsuario(String nombre, String email, String password);

    List<SegUsuario> findByNombre(String nombre);
    
    List<SegUsuario> findByEmail(String email);
}
