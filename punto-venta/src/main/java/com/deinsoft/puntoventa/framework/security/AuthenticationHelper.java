package com.deinsoft.puntoventa.framework.security;

import com.deinsoft.puntoventa.business.model.SegUsuario;
import com.deinsoft.puntoventa.business.repository.SegUsuarioRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
@Service
public class AuthenticationHelper {

    @Autowired
    SegUsuarioRepository segUsuarioRepository;
//
//    @Autowired
//    RoleService roleService;
//
//    @Autowired
//    RoleUserService roleUserService;
//
//    @Autowired
//    SecRoleUserService secRoleUserService;
//
//    @Autowired
//    CnfTenantService cnfTenantService;
//
//    public List<SecRole> getRolesByUser() {
//
//        SecUser user = this.getLoggedUserdata();
//        List<SecRole> profileList = roleService.getRolesByUser(user.getId());
//        return profileList;
//    }
//
//
    public SegUsuario getLoggedUserdata() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        return segUsuarioRepository.findByNombre(name).get(0); 
    }
//
//    public List<CnfTenant> getTenantByRole(long idRole) {
//        return cnfTenantService.getTenantByRole(idRole);
//    }
//
//    public List<SecRole> getAllProfiles() {
//        return roleService.findAll();
//    }

    /**
     * @see Function to get the values of logged user saved on jwt.
     * @param request
     * @return ArrayList with values of: idProfile or profiles, idCompanyGroup,
     * idCompany, idBranch
     */
    public Map<String, Object> getJwtLoggedUserData(HttpServletRequest request) {

        String jwt = request.getHeader(Constant.HEADER_AUTHORIZACION_KEY).replace("\"", "");
        Jws<Claims> claims = Jwts.parser()
                .setSigningKey(Constant.SUPER_SECRET_KEY)
                .parseClaimsJws(jwt.replace(Constant.TOKEN_BEARER_PREFIX + " ", ""));
        Map<String, Object> mp = new HashMap<>();

        //mp.put("idProfile",claims.getBody().get("prf").toString());
        mp.put("user", claims.getBody().get("user"));
        mp.put("userId", claims.getBody().get("id"));
        //mp.put("idCompanyGroup", claims.getBody().get("cg"));
        //mp.put("idCompany", claims.getBody().get("cia").toString());
        //mp.put("idBranch", claims.getBody().get("brn").toString());
        //mp.put("authorities", claims.getBody().get("authorities"));

        return mp;
    }

}
