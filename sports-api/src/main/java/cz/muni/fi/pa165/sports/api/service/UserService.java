/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.pa165.sports.api.service;

import cz.muni.fi.pa165.sports.api.dto.UserDto;
import java.util.List;

public interface UserService {

    /**
     * Create new user
     * @param user - data transfer object 
     * @return created user 
     */
    public UserDto createUser(UserDto user);
    
    /**
     * Update existing user
     * @param userDto
     * @return updated user 
     */
    public UserDto updateUser(UserDto userDto);

    /**
     * Delete existing user
     * @param userDto
     */
    public void deleteUser(UserDto userDto);

    
    /**
     * Change if the user is enabled
     * @param enabled - boolean value that determines if the user is enabled
     * or disabled
     */
//    public void setEnabledUser (Boolean enabled);
    
    /**
     * Find user by given id and returns the user.
     * @param username
     * @return user
     */
    public UserDto findUser(String username);
}
