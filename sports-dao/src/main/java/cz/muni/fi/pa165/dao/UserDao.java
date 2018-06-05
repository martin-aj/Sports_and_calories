/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Users;

/**
 *
 * @author mato
 */
public interface UserDao {
    /**
     * Stores new user into database. Id for the new user is automatically
     * generated and stored into id attribute.
     * Returns the added entry.
     * 
     * @param user user to be created.
     * @return 
     * @throws IllegalArgumentException when user is null, or user has already 
     * assigned id.
     */
    Users createUser(Users user);
    /**
     * Deletes user from database. 
     * 
     * @param user user to be deleted from db.
     * @throws IllegalArgumentException when user is null, or user has null id.
     */
    void deleteUser(Users user);

    /**
     * Updates user in database. Returns the updated entry.
     * 
     * @param user updated user to be stored into database.
     * @return 
     * @throws IllegalArgumentException when user is null, or user has null id.
     */
    Users updateUser (Users user);
    
    /**
     * Returns user with given username.
     * 
     * @param username primary key of requested user.
     * @return user with given username or null if such user does not exist.
     * @throws IllegalArgumentException when given id is null.
     */
    Users findUser (String username);
  
}
