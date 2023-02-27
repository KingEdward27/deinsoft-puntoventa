package com.deinsoft.puntoventa.framework.security;

import com.deinsoft.puntoventa.business.model.ActCajaTurno;
import com.deinsoft.puntoventa.business.model.SegMenu;
import com.deinsoft.puntoventa.business.model.SegPermiso;
import com.deinsoft.puntoventa.business.repository.ActCajaTurnoRepository;
import com.deinsoft.puntoventa.business.repository.SegMenuRepository;
import com.deinsoft.puntoventa.business.repository.SegPermisoRepository;
import com.deinsoft.puntoventa.framework.security.model.SecUser;
import com.deinsoft.puntoventa.framework.security.repository.SecUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.LocalDateTime;
import java.time.ZoneId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author bangulo
 *
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private SecUserRepository secUserRepository;
    
    private AuthenticationManager authenticationManager;

    private ActCajaTurnoRepository actCajaTurnoRepository;
    
    private SegPermisoRepository segPermisoRepository;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager,
            SecUserRepository secUserRepository,
            ActCajaTurnoRepository actCajaTurnoRepository,
            SegPermisoRepository segPermisoRepository) {
        this.authenticationManager = authenticationManager;
        this.secUserRepository = secUserRepository;
        this.actCajaTurnoRepository = actCajaTurnoRepository;
        this.segPermisoRepository = segPermisoRepository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            SecUser credenciales = new ObjectMapper().readValue(request.getInputStream(), SecUser.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    credenciales.getName(), credenciales.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication auth) throws IOException, ServletException {

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        LocalDateTime currentTime = LocalDateTime.now();
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + Constant.TOKEN_REFRESH_EXPIRATION_TIME * 10000 * 2/* *10000 maximo tiempo posible*/);
        LOGGER.info("expirationDate: " + expirationDate);
        String username = ((UserDetails) auth.getPrincipal()).getUsername();
        SecUser usuario = secUserRepository.findByName(username); 
        usuario.setPassword(null);
        
        ActCajaTurno caja = actCajaTurnoRepository.findBySegUsuarioId(usuario.getId())
                .stream().filter(item -> item.getFechaCierre() == null)
                .findFirst().orElse(new ActCajaTurno());
//        usuario.setActCajaTurno(caja);
        //String idUp = usuario.getUnidadPolicial().getIdUnidadPolicial();
//        List<SegPermiso> listMenu = null;
//        for (GrantedAuthority authority : ((User)auth.getPrincipal()).getAuthorities()) {
//            String rolName = authority.getAuthority().split("\\|")[0];
//            listMenu = segPermisoRepository.findBySegRolNonmbre(rolName);
//        }
        //Usuario usr = userService.getUserByUsername(usrname);
        String token = Jwts.builder().setIssuedAt(new Date()).setIssuer(Constant.ISSUER_INFO)
                .setId("DEINSOFT-JWT")
                .setSubject(((User) auth.getPrincipal()).getUsername())
                .claim("authorities",((User)auth.getPrincipal()).getAuthorities())
                .claim("user", usuario)
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(expirationDate)
                //.setExpiration(Date.from(ZonedDateTime.now().plusMinutes(TOKEN_EXPIRATION_TIME).toInstant()))
                .signWith(SignatureAlgorithm.HS512, Constant.SUPER_SECRET_KEY).compact();
        LOGGER.info("********* TOKEN: {}", Constant.TOKEN_BEARER_PREFIX + " " + token);
        response.addHeader(Constant.HEADER_AUTHORIZACION_KEY, Constant.TOKEN_BEARER_PREFIX + " " + token);
        response.addHeader("Access-Control-Expose-Headers", "Authorization");
        response.addHeader("Access-Control-Allow-Headers", "Authorization, X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept, X-Custom-header");

        SecurityContextHolder.getContext().setAuthentication(auth);
        LOGGER.info("Authorities:{} ", auth.getAuthorities());
        LOGGER.info("UserDetails:{} ", userDetails.toString());
        LOGGER.info("********** User has authorities: {}" + userDetails.getAuthorities());
    }
}
