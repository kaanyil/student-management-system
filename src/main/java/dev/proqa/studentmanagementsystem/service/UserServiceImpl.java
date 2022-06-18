package dev.proqa.studentmanagementsystem.service;

import dev.proqa.studentmanagementsystem.dto.AdminDTO;
import dev.proqa.studentmanagementsystem.exception.ResourceNotFoundException;
import dev.proqa.studentmanagementsystem.model.Role;
import dev.proqa.studentmanagementsystem.model.User;
import dev.proqa.studentmanagementsystem.model.enumeration.UserRole;
import dev.proqa.studentmanagementsystem.repository.RoleRepository;
import dev.proqa.studentmanagementsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final static String USER_NOT_FOUND_MSG = "user with id %d not found";


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username).get();
        if(user == null){
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found in the database: {}", username);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        SimpleGrantedAuthority userAuthority = new SimpleGrantedAuthority(
                user.getRole().getUserRole().name());

        authorities.add(new SimpleGrantedAuthority(user.getRole().getUserRole().name()));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities);
    }

    @Override
    public User saveUser(User user) {
        log.info("Saving new user {} to the database", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }


    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getUserRole().name());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String userName, UserRole userRole) {
        log.info("Adding new role {} to the user {}", userRole, userName);
        User user = userRepo.findByUsername(userName).get();
        Role role = roleRepo.findByUserRole(userRole).get();
        user.setRole(role);
    }

    @Override
    public User getUserByName(String userName) {
        log.info("Fetching user {}", userName);
        return userRepo.findByUsername(userName).get();
    }

    @Override
    public User findById(Long id) throws ResourceNotFoundException{
        return userRepo.findByIdOrderByFirstName(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(USER_NOT_FOUND_MSG, id)));
    }

    @Override
    public List<User> fetchAllUsers() {
        log.info("Fetching all users");
        return userRepo.findAll();
    }

    @Override
    public void register(AdminDTO user) {

    }

}

