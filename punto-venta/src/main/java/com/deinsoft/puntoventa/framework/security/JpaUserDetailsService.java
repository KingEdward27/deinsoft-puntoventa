package com.deinsoft.puntoventa.framework.security;

import com.deinsoft.puntoventa.framework.repository.JdbcRepository;
import com.deinsoft.puntoventa.framework.security.model.SecRoleUser;
import com.deinsoft.puntoventa.framework.security.model.SecUser;
import com.deinsoft.puntoventa.framework.security.repository.SecRoleUserRepository;
import com.deinsoft.puntoventa.framework.security.repository.SecUserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("jpaUserDetailsService")
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private SecUserRepository secUserRepository;

    @Autowired
    private SecRoleUserRepository secRoleUserRepository;
    
    @Autowired
    JdbcRepository jdbcRepository;
    private Logger logger = LoggerFactory.getLogger(JpaUserDetailsService.class);

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        SecUser usuario = secUserRepository.findByEmail(email);

        if (usuario == null) {
            logger.error("Error en el Login: no existe el usuario '" + email + "' en el sistema!");
            throw new UsernameNotFoundException("Username: " + email + " no existe en el sistema!");
        }
//        Set<SecRoleUser> listRoles = secRoleUserRepository.findBySecUserId(usuario.getId());
//        usuario.setListSecRoleUser(listRoles);
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        //Map<String,Object> list = jdbcRepository.selectColumnsMap(username, username, username)
        for (SecRoleUser roleUser : usuario.getListSecRoleUser()) {
            logger.info("Role: ".concat(roleUser.getSecRole().getName()));
            authorities.add(new SimpleGrantedAuthority(roleUser.getSecRole().getName()));
        }

        if (authorities.isEmpty()) {
            logger.error("Error en el Login: Usuario '" + email + "' no tiene roles asignados!");
            throw new UsernameNotFoundException("Error en el Login: usuario '" + email + "' no tiene roles asignados!");
        }

        return new User(usuario.getName(), usuario.getPassword(), true, true, true, true, authorities);
    }

}
