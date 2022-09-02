package com.deinsoft.puntoventa.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deinsoft.puntoventa.business.model.SegMenu;

public interface SegMenuRepository extends JpaRepository<SegMenu, Long> {

    @Query(value = "select p from segMenu p "
            + "where upper(p.nombre) like %?1% "
            + "and (p.icon is null or upper(p.icon) like %?2%) "
            + "and (p.path is null or upper(p.path) like %?3%) ")

    List<SegMenu> getAllSegMenu(String nombre, String icon, String path);

    @Query(value = "select p from segMenu p "
            + "where p.parent.id =  ?1 ")
    List<SegMenu> findBySegMenuId(long id);

}
