package dev.proqa.studentmanagementsystem.service;

import dev.proqa.studentmanagementsystem.dto.StudentDTO;
import dev.proqa.studentmanagementsystem.exception.BadRequestException;
import dev.proqa.studentmanagementsystem.exception.ConflictException;
import dev.proqa.studentmanagementsystem.exception.ResourceNotFoundException;
import dev.proqa.studentmanagementsystem.model.Department;
import dev.proqa.studentmanagementsystem.model.Student;
import dev.proqa.studentmanagementsystem.model.User;
import dev.proqa.studentmanagementsystem.repository.DepartmentRepository;
import dev.proqa.studentmanagementsystem.repository.RoleRepository;
import dev.proqa.studentmanagementsystem.repository.StudentRepository;
import dev.proqa.studentmanagementsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final static String USER_NOT_FOUND_MSG = "user with id %d not found";
    private final static String STUDENT_NOT_FOUND_MSG = "student with id %d not found";
    private final StudentRepository studentRepo;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    public List<StudentDTO> findAll() {
        return studentRepo.findAllBy();
    }

    public StudentDTO findByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(USER_NOT_FOUND_MSG, userId)));

        return studentRepo.findByUserIdOrderByUserId(user).orElseThrow(() ->
                new ResourceNotFoundException(String.format(STUDENT_NOT_FOUND_MSG, userId)));
    }

    public StudentDTO findById(Long id) {
        return studentRepo.findByIdOrderById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(STUDENT_NOT_FOUND_MSG, id)));
    }


    public void addStudent(Student student)
            throws BadRequestException {

         student = Student.builder()
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .email(student.getEmail())
                .username(student.getUsername())
                .address(student.getAddress())
                .city(student.getCity())
                .state(student.getState())
                .zipCode(student.getZipCode())
                .country(student.getCountry())
                .phoneNumber(student.getPhoneNumber())
                .gender(student.getGender())
                .role(student.getRole())
                .build();

        Department department = departmentRepository.getById(student.getId());
        student.setDepartment(department);

        String encodedPassword = passwordEncoder.encode(student.getPassword());
        student.setPassword(encodedPassword);


        if (studentRepo.existsByEmail(student.getEmail())) {
            throw new ConflictException("Error: Email is already in use!");
        }

        if (studentRepo.existsByUsername(student.getUsername())) {
            throw new ConflictException("Error: Username is already in use!");
        }

        studentRepo.save(student);
    }


}



