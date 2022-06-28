package com.example.UserManagement.services;

import com.example.UserManagement.model.Permission;
import com.example.UserManagement.repositories.PermissionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {

    private final PermissionRepository permissionRepository;

    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public List<Permission> getAll(){
        return (List<Permission>) permissionRepository.findAll();
    }
}
