/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.UserRoles;
import java.util.List;

/**
 *
 * @author mato
 */
public interface UserRoleDao {
    
    public UserRoles createUserRole(UserRoles userRole);
            
    /**
     * Returns list of all user roles in the database.
     * 
     * @return list of all user roles in database.
     * @throws  ServiceFailureException when db operation fails.
     */
    List<UserRoles> listUserRoles ();
    
    public void deleteUserRole(UserRoles userRole);
    
    public UserRoles updateUser(UserRoles userRoles);
}
