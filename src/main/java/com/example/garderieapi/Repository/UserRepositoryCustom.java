package com.example.garderieapi.Repository;

import com.example.garderieapi.entity.Role;

import java.util.List;

public interface UserRepositoryCustom {
    List<Role> findRolesByUsername(String nom);
}
