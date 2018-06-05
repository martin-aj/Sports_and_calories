/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.pa165.service.impl;

import cz.muni.fi.pa165.dao.UserDao;
import cz.muni.fi.pa165.dao.UserRoleDao;
import cz.muni.fi.pa165.entity.UserRoles;
import cz.muni.fi.pa165.sports.api.dto.UserRoleDto;
import cz.muni.fi.pa165.sports.api.service.UserRoleService;
import java.util.ArrayList;
import java.util.List;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mato
 */
@Transactional
public class UserRoleServiceImpl implements UserRoleService{
    
    //=============  Constants  ================================================
    private final String ROLE_ADMIN = "ROLE_ADMIN";
    
    //=============  Attributes  ===============================================
    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private Mapper mapper;


    public UserRoleServiceImpl() {
    }

    public UserRoleServiceImpl(UserRoleDao userRoleDao, Mapper mapper) {
        this.userRoleDao = userRoleDao;
        this.mapper = mapper;
    }

    //=============  Methods  ==================================================
    

    @Override
    @Secured (ROLE_ADMIN)
    public List<UserRoleDto> listUserRoles() {
        ArrayList<UserRoles> userRolesList = (ArrayList<UserRoles>) userRoleDao.listUserRoles();
        List<UserRoleDto> userRolesDtoList = new ArrayList<>();
        for (UserRoles userRole : userRolesList) {
            userRolesDtoList.add(mapToDto(userRole));
        }
        return userRolesDtoList;
    }
    private UserRoleDto mapToDto(UserRoles userRole) {
        return userRole != null ? mapper.map(userRole, UserRoleDto.class) : null;
    }
    /** used when updating userRole - probably won't be used */
    private UserRoles mapFromDto(UserRoleDto userRoleDto) {
        return userRoleDto != null ? mapper.map(userRoleDto, UserRoles.class) : null;
    }

    @Override
    @Secured (ROLE_ADMIN)
    public UserRoleDto createUserRole(UserRoleDto userRole) {
        if (userRole == null) {
            throw new IllegalArgumentException("User nickname cannot be null");
        }
        
        UserRoles userRole1 = mapFromDto(userRole);
        userRoleDao.createUserRole(userRole1);
        userRole.setId(userRole1.getId());
        return userRole;
    }

    @Override
    @Secured (ROLE_ADMIN)
    public UserRoleDto updateUserRole(UserRoleDto userRoleDto) {
        if (userRoleDto == null) {
            throw new IllegalArgumentException("Username cannot be null");
        }
        UserRoles userRole = mapFromDto(userRoleDto);
        userRoleDao.updateUser(userRole);
        userRoleDto.setId(Long.MIN_VALUE);
        return userRoleDto;
    }

    @Override
    @Secured (ROLE_ADMIN)
    public void deleteUserRole(UserRoleDto userRoleDto) {
        if (userRoleDto == null) {
            throw new IllegalArgumentException("User role cannot be null");
        }
        UserRoles userRole = mapFromDto(userRoleDto);
        userRoleDao.deleteUserRole(userRole);
    }
}
