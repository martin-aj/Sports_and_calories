/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.pa165.sports.api.dto;

public class UserRoleDto {

    private Long id;

    private UserDto users;
    
    private String role;

    /**
     * @return the users
     */
    public UserDto getUsers() {
        return users;
    }

    /**
     * @param userDto
     */
    public void setUsers(UserDto userDto) {
        this.users = userDto;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }  
}
