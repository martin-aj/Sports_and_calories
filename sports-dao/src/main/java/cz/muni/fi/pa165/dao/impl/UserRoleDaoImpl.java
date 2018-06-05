/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.dao.impl;

import cz.muni.fi.pa165.dao.UserRoleDao;
import cz.muni.fi.pa165.entity.UserRoles;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.util.Assert;

/**
 *
 * @author mato
 */
public class UserRoleDaoImpl implements UserRoleDao {

    @PersistenceContext
    private EntityManager em;

    public UserRoleDaoImpl() {
    }

    @Override
    public List<UserRoles> listUserRoles() {
        String sql = "SELECT s FROM UserRoles s";
        Query query = em.createQuery(sql, UserRoles.class);

        try {
            List<UserRoles> userRoles = (List<UserRoles>) query.getResultList();
            return userRoles;
        } catch (NoResultException ex) {
            return null;
        } catch (Exception ex) {
            if (ex instanceof DataAccessException) {
                throw ex;
            } else {
                throw new DataAccessResourceFailureException("Load of entity was unsuccessfull.", ex);
            }
        }
    }
    
    @Override
    public UserRoles createUserRole(UserRoles userRole) {
        if (userRole == null) {
            return null;
        }

        try {
            em.persist(userRole);
            return userRole;
        } catch (Exception ex) {
            if (ex instanceof DataAccessException) {
                throw ex;
            } else {
                throw new DataAccessResourceFailureException("Creation of entity was unsuccessfull.", ex);
            }
        }
    }
    
    @Override
    public UserRoles updateUser(UserRoles userRoles) {
        if (userRoles == null) {
            throw new IllegalArgumentException("Update: user rle cannot be updated, wrong parameter");
        }

        try {
            em.merge(userRoles);
            UserRoles user_upd = em.find(UserRoles.class, userRoles.getId());
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
    public void deleteUserRole(UserRoles userRole) {
        try {
            Assert.notNull(userRole, "UserRole is not set");
            em.remove(em.contains(userRole) ? userRole : em.merge(userRole));
        } catch (Exception ex) {
            if (ex instanceof DataAccessException) {
                throw ex;
            } else {
                throw new DataAccessResourceFailureException("Deletion of entity was unsuccessfull.", ex);
            }
        }
    }

}
