package com.cupidconnect.cupidconnect.services.impl;


import com.cupidconnect.cupidconnect.models.RoleEntity;
import com.cupidconnect.cupidconnect.repositories.RoleRepository;
import com.cupidconnect.cupidconnect.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public RoleEntity createRole(RoleEntity roleEntity) {
        return roleRepository.save(roleEntity);
    }

    @Override
    public RoleEntity getRoleById(long id) {
        return roleRepository.findById(id).orElseThrow(()-> new RuntimeException("Role not found"));
    }

    @Override
    public List<RoleEntity> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public RoleEntity updateRole(long id, RoleEntity newRoleEntity) {
        return null;
    }

    @Override
    public void deleteRole(long id) {

    }
}
