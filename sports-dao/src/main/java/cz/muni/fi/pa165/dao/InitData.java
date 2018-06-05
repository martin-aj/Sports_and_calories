/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.RoleTypes;
import cz.muni.fi.pa165.entity.UserRoles;
import cz.muni.fi.pa165.entity.Users;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.transaction.annotation.Transactional;

/**
 * Initialize basic data on every context initialization: - create basic users and assign them appropriate roles.
 *
 * @author mato
 */
@Transactional
public class InitData implements ApplicationListener<ContextRefreshedEvent> {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRoleDao userRoleDao;

    private static boolean executed = false;

    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        // do what you want - you can use all spring beans (this is the difference between init-method and @PostConstructor where you can't)
        // this class can be annotated as spring service, and you can use @Autowired in it
        if (!executed) {
            executed = true;

            Users admin = userDao.findUser("admin");
            if (admin == null) {
                admin = new Users();
                admin.setUsername("admin");
                String generatedSecuredPasswordHash1 = BCrypt.hashpw("heslo", BCrypt.gensalt(12));
                admin.setPassword(generatedSecuredPasswordHash1);
                userDao.createUser(admin);
            }

            Users user = userDao.findUser("user");
            if (user == null) {
                user = new Users();
                user.setUsername("user");
                String generatedSecuredPasswordHash2 = BCrypt.hashpw("heslo", BCrypt.gensalt(12));
                user.setPassword(generatedSecuredPasswordHash2);
                userDao.createUser(user);
            }

            if (userRoleDao.listUserRoles() == null || userRoleDao.listUserRoles().isEmpty()) {
                UserRoles adminAdminRole = new UserRoles();
                adminAdminRole.setRole(RoleTypes.ROLE_ADMIN.name());
                adminAdminRole.setUsers(admin);
                userRoleDao.createUserRole(adminAdminRole);

                UserRoles adminUserRole = new UserRoles();
                adminUserRole.setRole(RoleTypes.ROLE_USER.name());
                adminUserRole.setUsers(admin);
                userRoleDao.createUserRole(adminUserRole);

                UserRoles userUserRole = new UserRoles();
                userUserRole.setRole(RoleTypes.ROLE_USER.name());
                userUserRole.setUsers(user);
                userRoleDao.createUserRole(userUserRole);
            }
        }
    }
}
