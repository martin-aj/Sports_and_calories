/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.service.impl;

import cz.muni.fi.pa165.dao.SportActivityDao;
import cz.muni.fi.pa165.dao.UserDao;
import cz.muni.fi.pa165.entity.Users;
import cz.muni.fi.pa165.sports.api.dto.PerformedActivityDto;
import cz.muni.fi.pa165.sports.api.dto.UserDto;
import cz.muni.fi.pa165.sports.api.service.PerformedActivityService;
import cz.muni.fi.pa165.sports.api.service.UserService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
public class UserServiceImpl implements UserService {

    //=============  Constants  ================================================
    private final String ROLE_ADMIN = "ROLE_ADMIN";
    
    //=============  Attributes  ===============================================
    @Autowired
    private UserDao userDao;


    @Autowired
    private Mapper mapper;


    public UserServiceImpl() {
    }

    public UserServiceImpl(UserDao userDao, Mapper mapper) {
        this.userDao = userDao;
        this.mapper = mapper;

    }

    //=============  Methods  ==================================================
    @Override
    @Secured(ROLE_ADMIN)
    public UserDto createUser(UserDto userDto) {
        if (userDto == null) {
            throw new IllegalArgumentException("User nickname cannot be null");
        }
        Users user1 = mapFromDto(userDto);
        userDao.createUser(user1);
        userDto.setUsername(user1.getUsername());
        return userDto;
    }

    @Override
    @Secured(ROLE_ADMIN)
    public UserDto findUser(String username) {
        Users user = userDao.findUser(username);
        return mapToDto(user);
    }

    @Override
    @Secured(ROLE_ADMIN)
    public UserDto updateUser(UserDto userDto) {
        if (userDto == null) {
            throw new IllegalArgumentException("Username cannot be null");
        }
        Users user = mapFromDto(userDto);
        userDao.updateUser(user);
        userDto.setUsername(user.getUsername());
        return userDto;
    }

    @Override
    @Secured(ROLE_ADMIN)
    public void deleteUser(UserDto userDto) {
        if (userDto == null) {
            throw new IllegalArgumentException("User nickname cannot be null");
        }
        Users user = mapFromDto(userDto);
        userDao.deleteUser(user);
    }


    private UserDto mapToDto(Users user) {
        return user != null ? mapper.map(user, UserDto.class) : null;
    }

    private Users mapFromDto(UserDto userDto) {
        return userDto != null ? mapper.map(userDto, Users.class) : null;
    }


}
