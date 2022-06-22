/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deinsoft.puntoventa.framework.security.repository;

import com.deinsoft.puntoventa.framework.security.model.SecRoleUser;
import com.deinsoft.puntoventa.framework.security.model.SecUser;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 *
 * @author EDWARD-PC
 */
public interface SecRoleUserRepository  extends JpaRepository<SecRoleUser,Long>{
   public Set<SecRoleUser> findBySecUserId(long id);
}
