package com.deinsoft.puntoventa.business.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.SegPermiso;
import com.deinsoft.puntoventa.business.repository.SegPermisoRepository;
import com.deinsoft.puntoventa.business.service.SegPermisoService;
import com.deinsoft.puntoventa.business.commons.service.CommonServiceImpl;
import com.deinsoft.puntoventa.business.model.SegMenu;
import com.deinsoft.puntoventa.business.repository.SegMenuRepository;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Transactional
public class SegPermisoServiceImpl extends CommonServiceImpl<SegPermiso, SegPermisoRepository> implements SegPermisoService {

    @Autowired
    SegPermisoRepository segPermisoRepository;

    @Autowired
    SegMenuRepository segMenuRepository;
    
    public List<SegPermiso> getAllSegPermiso(SegPermiso segPermiso) {
        List<SegPermiso> segPermisoList = (List<SegPermiso>) segPermisoRepository.getAllSegPermiso();
        return segPermisoList;
    }

    public SegPermiso getSegPermiso(Long id) {
        SegPermiso segPermiso = null;
        Optional<SegPermiso> segPermisoOptional = segPermisoRepository.findById(id);
        if (segPermisoOptional.isPresent()) {
            segPermiso = segPermisoOptional.get();
        }
        return segPermiso;
    }

    public SegPermiso saveSegPermiso(SegPermiso segPermiso) {
        SegPermiso segPermisoResult = segPermisoRepository.save(segPermiso);
        return segPermisoResult;
    }

    public List<SegPermiso> getAllSegPermiso() {
        List<SegPermiso> segPermisoList = (List<SegPermiso>) segPermisoRepository.findAll();
        return segPermisoList;
    }

    public List<SegPermiso> getAllSegPermisoBySegRol(long id) {
        List<SegPermiso> SegPermisoList = (List<SegPermiso>) segPermisoRepository.findBySegRolId(id);
        return SegPermisoList;
    }

    public List<SegPermiso> getAllSegPermisoBySegMenu(long id) {
        List<SegPermiso> SegPermisoList = (List<SegPermiso>) segPermisoRepository.findBySegMenuId(id);
        return SegPermisoList;
    }

    public List<SegPermiso> getAllSegPermisoBySegAccion(long id) {
        List<SegPermiso> SegPermisoList = (List<SegPermiso>) segPermisoRepository.findBySegAccionId(id);
        return SegPermisoList;
    }

    @Override
    public void delete(long id) {
        SegPermiso segPermiso = null;
        Optional<SegPermiso> segPermisoOptional = segPermisoRepository.findById(id);

        if (segPermisoOptional.isPresent()) {
            segPermiso = segPermisoOptional.get();
            segPermisoRepository.delete(segPermiso);
        }
    }
    
    public List<SegPermiso> getAllSegPermisoBySegRolNombre(String nombre) {
        
        
        
        if (nombre.equalsIgnoreCase("ROLE_SUPER_ADMIN")){
            List<SegPermiso> segPermisoList = new ArrayList<>();
            List<SegMenu> segMenuList = segMenuRepository.findAll();
            segMenuList.forEach(data -> {
                SegPermiso s = new SegPermiso();
                s.setSegMenu(data);
                segPermisoList.add(s);
            });
            return segPermisoList;
        } else {
            final List<SegPermiso> segPermisoList = (List<SegPermiso>) segPermisoRepository.getAllSegPermiso();
            
            return segPermisoList.stream()
                    .filter( distinctByKey(p -> p.getSegMenu().getNombre()))
                    .map(item -> {
                        Set<SegMenu> list = 
                                item.getSegMenu().getChildren().stream()
                                        .filter(predicate -> {
                                          return segPermisoList.stream().anyMatch(i -> i.getSegMenu().getId() == predicate.getId());
                                        })
                                        .collect(Collectors.toSet());
                        item.getSegMenu().setChildren(list);
                        return item;
                    })
                    .collect( Collectors.toList());
        }
        
    }
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
