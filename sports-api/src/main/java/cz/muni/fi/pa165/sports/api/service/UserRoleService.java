/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.pa165.sports.api.service;

import cz.muni.fi.pa165.sports.api.dto.UserRoleDto;
import java.util.List;

/**
 *
 * @author mato
 */
public interface UserRoleService {
    /**
     * Return list of user roles.
     * @return user roles
     */
    public List <UserRoleDto> listUserRoles();
    
    /**
     * Create new user
     * @param userRole - data transfer object 
     * @return created user role
     */
    public UserRoleDto createUserRole(UserRoleDto userRole);
    
    /**
     * Update existing user
     * @param userRoleDto
     * @return updated user role
     */
    public UserRoleDto updateUserRole(UserRoleDto userRoleDto);

    /**
     * Delete existing user
     * @param userRoleDto
     */
    public void deleteUserRole(UserRoleDto userRoleDto);
}
