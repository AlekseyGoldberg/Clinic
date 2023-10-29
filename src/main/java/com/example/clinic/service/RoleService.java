package com.example.clinic.service;

import com.example.clinic.entity.Role;
import com.example.clinic.exception.CommonBaseException;
import com.example.clinic.message.ExceptionMessage;
import com.example.clinic.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleService {
    private RoleRepository roleRepository;

    public Role getByNameRole(String nameRole) {
        existRoleByName(nameRole);
        return roleRepository.findByRole(nameRole);
    }

    public void existRoleByName(String nameRole) {
        if (!roleRepository.existsByRole(nameRole)) {
            throw new CommonBaseException(String.format(ExceptionMessage.ROLE_NOT_EXIST, nameRole));
        }
    }
}
