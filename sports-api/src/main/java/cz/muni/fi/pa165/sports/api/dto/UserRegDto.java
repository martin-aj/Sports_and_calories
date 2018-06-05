/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.sports.api.dto;

public class UserRegDto {
    private String username;
    private String password;
    private String roleForUser;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleForUser() {
        return roleForUser;
    }

    public void setRoleForUser(String roleForUser) {
        this.roleForUser = roleForUser;
    }
    
    
}
