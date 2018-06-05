/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.pa165.dao.impl;

import cz.muni.fi.pa165.dao.UserDao;
import cz.muni.fi.pa165.entity.Users;
import cz.muni.fi.pa165.entity.Users;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;

/**
 *
 * @author mato
 */
public class UserDaoImpl implements UserDao{

    @PersistenceContext
    private EntityManager em;


    public UserDaoImpl() {
    }
    
    @Override
    public Users createUser(Users user) {
        if (user == null) {
            throw new IllegalArgumentException("Create: user cannot be created, wrong parameter");
        }
        try {
            em.persist(user);
            return user;
        } catch (Exception ex) {
            if (ex instanceof DataAccessException) {
                throw ex;
            } else {
                throw new DataAccessResourceFailureException("Creation of entity was unsuccessfull.", ex);
            }
        }
    }

    @Override
    public void deleteUser(Users user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Users updateUser(Users user) {
        if (user == null) {
            throw new IllegalArgumentException("Update: user cannot be updated, wrong parameter");
        }

        try {
            em.merge(user);
            Users user_upd = em.find(Users.class, user.getUsername());
            return user_upd;
        } catch (Exception ex) {
            if (ex instanceof DataAccessException) {
                throw ex;
            } else {
                throw new DataAccessResourceFailureException("Update of entity was unsuccessfull.", ex);
            }
        }
    }

    @Override
    public Users findUser(String username) {
        try {
            Users user = em.find(Users.class, username);
            return user;
        } catch (javax.persistence.NoResultException ex) {
            return null;
        } catch (Exception ex) {
            if (ex instanceof DataAccessException) {
                throw ex;
            } else {
                throw new DataAccessResourceFailureException("Load of entity was unsuccessfull.", ex);
            }
        }
    }
    
}
