package com.example.UserManagement.repositories;

import com.example.UserManagement.model.Permission;
import org.springframework.data.repository.CrudRepository;

public interface PermissionRepository extends CrudRepository<Permission,Long> {
}
