package com.cupidconnect.cupidconnect.services;

import com.cupidconnect.cupidconnect.models.RoleEntity;

import java.util.List;

public interface RoleService {
     RoleEntity createRole(RoleEntity roleEntity);
     RoleEntity getRoleById(long id);

     List<RoleEntity> getAllRoles();

     RoleEntity updateRole(long id, RoleEntity newRoleEntity);

     void deleteRole(long id);
}
