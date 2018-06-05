/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.entity;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author mato
 */
public enum RoleTypes {

    ROLE_USER("ROLE_USER"),
    //    ROLE_USER {
    //      @Override
    //      public String toString() {
    //          return "ROLE_USER";
    //      }
    //  },

    ROLE_ADMIN("ROLE_ADMIN");
//          {
//      @Override
//      public String toString() {
//          return "ROLE_ADMIN";
//      }
//  }
    private String roleTypes;

    private RoleTypes(String roleTypes) {
        this.roleTypes = roleTypes;
    }

    private static Map<String, RoleTypes> mapRoleTypes = new HashMap<String, RoleTypes>();

    static {
        for (RoleTypes r : EnumSet.allOf(RoleTypes.class)) {
            mapRoleTypes.put(r.toString(), r);
        }
    }

    @Override
    public String toString() {
        return roleTypes;
    }

    public String getRoleTypes() {
        return roleTypes;
    }

}
