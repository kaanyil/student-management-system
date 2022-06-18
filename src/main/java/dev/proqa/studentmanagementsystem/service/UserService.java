package dev.proqa.studentmanagementsystem.service;

import dev.proqa.studentmanagementsystem.dto.AdminDTO;
import dev.proqa.studentmanagementsystem.model.Role;
import dev.proqa.studentmanagementsystem.model.User;
import dev.proqa.studentmanagementsystem.model.enumeration.UserRole;


import java.util.List;

public interface UserService {

    User saveUser(User user);

    Role saveRole(Role role);

    void addRoleToUser(String userName, UserRole userRole);

    User getUserByName(String userName);

    User findById(Long id);

    List<User> fetchAllUsers();

    void register(AdminDTO user);
}
