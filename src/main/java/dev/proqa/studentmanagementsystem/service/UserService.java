package dev.proqa.studentmanagementsystem.service;

import dev.proqa.studentmanagementsystem.dto.StudentDTO;
import dev.proqa.studentmanagementsystem.dto.UserDTO;
import dev.proqa.studentmanagementsystem.exception.AuthException;
import dev.proqa.studentmanagementsystem.exception.BadRequestException;
import dev.proqa.studentmanagementsystem.exception.ConflictException;
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

        Role userRole = roleRepo.findByName(UserRole.ROLE_STUDENT)
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
                userDTO.getZipCode(), userDTO.getState(), userDTO.getCountry(), userDTO.getGender());

    }
}

