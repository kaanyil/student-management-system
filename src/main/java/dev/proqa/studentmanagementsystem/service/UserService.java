package dev.proqa.studentmanagementsystem.service;

import dev.proqa.studentmanagementsystem.dto.AdminDTO;
import dev.proqa.studentmanagementsystem.dto.StudentDTO;
import dev.proqa.studentmanagementsystem.dto.UserDTO;
import dev.proqa.studentmanagementsystem.exception.AuthException;
import dev.proqa.studentmanagementsystem.exception.BadRequestException;
import dev.proqa.studentmanagementsystem.exception.ConflictException;
import dev.proqa.studentmanagementsystem.exception.ResourceNotFoundException;
import dev.proqa.studentmanagementsystem.model.Role;
import dev.proqa.studentmanagementsystem.model.Student;
import dev.proqa.studentmanagementsystem.model.User;
import dev.proqa.studentmanagementsystem.model.enumeration.UserRole;
import dev.proqa.studentmanagementsystem.repository.RoleRepository;
import dev.proqa.studentmanagementsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.lang.module.ResolutionException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final static String USER_NOT_FOUND_MSG = "user with id %d not found";


    public List<UserDTO> fetchAllUsers() {
        return userRepo.findAllBy();
    }

    public List<UserDTO> fetchAllStudents() {
        Role role = roleRepo.getByUserRole(UserRole.ROLE_STUDENT);
        return userRepo.findByRole(role);
    }

    public UserDTO findById(Long id) {
        return userRepo.findByIdOrderById(id).
                orElseThrow(() -> new ResolutionException(String.format(USER_NOT_FOUND_MSG, id)));
    }


    public void register(User user) {

        if (userRepo.existsByEmail(user.getEmail())) {
            throw new ConflictException("Error: Email is already in use!");
        }

        if (userRepo.existsByUsername(user.getUsername())) {
            throw new ConflictException("Error: Username is already in use!");
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        Role userRole = roleRepo.findByUserRole(UserRole.ROLE_STUDENT)
                .orElseThrow(() -> new ResolutionException("Error: Role is not found."));

        user.setRole(userRole);
        userRepo.save(user);
    }


    public void login(String username, String password) throws AuthException {
        try {
            Optional<User> user = userRepo.findByUsername(username);


            if (!BCrypt.checkpw(password, user.get().getPassword()))
                throw new AuthException("Invalid credentials");
        } catch (Exception e) {
            throw new AuthException("Invalid credentials");
        }
    }

    public void addUserAuth(AdminDTO adminDTO) throws BadRequestException {

        boolean emailExist = userRepo.existsByEmail(adminDTO.getEmail());
        boolean usernameExist = userRepo.existsByUsername(adminDTO.getUsername());

        if (emailExist) {
            throw new BadRequestException("Error: Email already in use!");
        }

        if (usernameExist) {
            throw new BadRequestException("Error: Username already in use!");
        }

        String encodedPassword = passwordEncoder.encode(adminDTO.getPassword());
        adminDTO.setPassword(encodedPassword);

        Role role = addRole(adminDTO.getRole());

        User updatedUser = new User(adminDTO.getFirstName(), adminDTO.getLastName(),
                adminDTO.getEmail(), adminDTO.getUsername(), adminDTO.getPassword(),
                adminDTO.getAddress(), adminDTO.getCity(), adminDTO.getState(),
                adminDTO.getZipCode(), adminDTO.getCountry(), adminDTO.getPhoneNumber(), adminDTO.getGender(),
                role);

        userRepo.save(updatedUser);
    }



    public void updateUser(Long id, UserDTO userDTO) {
        boolean emailExist = userRepo.existsByEmail(userDTO.getEmail());
        boolean userNameExist = userRepo.existsByUsername(userDTO.getUsername());

        User user = userRepo.findById(id).get();

        if (emailExist && !userDTO.getEmail().equals(user.getEmail())) {
            throw new BadRequestException("Error: Email is already registered in the system");
        }

        if (userNameExist && !userDTO.getUsername().equals(user.getUsername())) {
            throw new BadRequestException("Error: Username is already registered in the system");
        }

        userRepo.update(id, userDTO.getFirstName(), userDTO.getLastName(), userDTO.getPhoneNumber(),
                userDTO.getEmail(), userDTO.getUsername(), userDTO.getAddress(), userDTO.getCity(),
                userDTO.getZipCode(), userDTO.getState(), userDTO.getCountry(), userDTO.getGender().name());

    }

    public void updatePassword(Long id, String newPassword, String oldPassword) throws BadRequestException {

        User user = userRepo.getById(id);

        if (!BCrypt.hashpw(oldPassword, user.getPassword()).equals(user.getPassword()))
            throw new BadRequestException("password does not match");

        String hashedPassword = passwordEncoder.encode(newPassword);

        user.setPassword(hashedPassword);

        userRepo.save(user);
    }

    public void updateUserAuth(Long id, AdminDTO adminDTO) throws BadRequestException {
        User user = userRepo.findById(id).orElseThrow(() ->
                new ResolutionException(String.format(USER_NOT_FOUND_MSG, id)));

        boolean emailExist = userRepo.existsByEmail(adminDTO.getEmail());
        boolean usernameExist = userRepo.existsByUsername(adminDTO.getUsername());

        if (emailExist && !adminDTO.getEmail().equals(user.getEmail())) {
            throw new BadRequestException("Error: Email already in use!");
        }

        if (usernameExist && !adminDTO.getUsername().equals(user.getUsername())) {
            throw new BadRequestException("Error: Username already in use!");
        }

        if (adminDTO.getPassword() == null)
            adminDTO.setPassword(user.getPassword());

        else {
            String encodedPassword = passwordEncoder.encode(adminDTO.getPassword());
            adminDTO.setPassword(encodedPassword);
        }

        Role role = addRole(adminDTO.getRole());

        User updatedUser = new User(id, adminDTO.getFirstName(), adminDTO.getLastName(),
                adminDTO.getEmail(), adminDTO.getUsername(), adminDTO.getPassword(),
                adminDTO.getAddress(), adminDTO.getCity(), adminDTO.getState(),
                adminDTO.getZipCode(), adminDTO.getCountry(), adminDTO.getPhoneNumber(), adminDTO.getGender(),
                role);

        userRepo.save(updatedUser);
    }

    public void removeById(Long id) throws ResourceNotFoundException {
        userRepo.findById(id).orElseThrow(() ->
                new ResolutionException(String.format(USER_NOT_FOUND_MSG, id)));

        userRepo.deleteById(id);
    }

    private Role addRole(String role) {

        if (role == null){
            return roleRepo.findByUserRole(UserRole.ROLE_STUDENT)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        }
        else {
            if ("Administrator".equals(role)) {
                return roleRepo.findByUserRole(UserRole.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            }
            else {
                return roleRepo.findByUserRole(UserRole.ROLE_STUDENT)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            }
        }
    }

}

