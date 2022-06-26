package dev.proqa.studentmanagementsystem.service;

import dev.proqa.studentmanagementsystem.dto.StudentDTO;
import dev.proqa.studentmanagementsystem.exception.BadRequestException;
import dev.proqa.studentmanagementsystem.exception.ConflictException;
import dev.proqa.studentmanagementsystem.exception.ResourceNotFoundException;
import dev.proqa.studentmanagementsystem.model.Department;
import dev.proqa.studentmanagementsystem.model.Student;
import dev.proqa.studentmanagementsystem.model.User;
import dev.proqa.studentmanagementsystem.model.enumeration.Departments;
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

    private final StudentRepository studentRepo;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    private final static String USER_NOT_FOUND_MSG = "user with id %d not found";
    private final static String STUDENT_NOT_FOUND_MSG = "student with id %d not found";

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


//    public List<StudentDTO> findStudentsByDepartmentName(Departments departmentName) {
//        Department department = departmentRepository.findByName(departmentName)
//                .orElseThrow(() -> new RuntimeException("Error: Department is not found."));
//
//        return studentRepo.findByDepartment(department);
//    }
//
//
//    public void addStudent(Long userId, StudentDTO studentDTO)
//            throws BadRequestException {
//        User user = userRepository.findById(userId).orElseThrow(() ->
//                new ResourceNotFoundException(String.format(USER_NOT_FOUND_MSG, userId)));
//
//        Department departments = departmentRepository.findByName(studentDTO.getDepartment())
//                .orElseThrow(() -> new RuntimeException("Error: Department is not found."));
//
//        Student student = new Student(user, departments);
//
//        studentRepo.save(student);
//
//    }
//
//    public void updateStudent(Long userId, StudentDTO studentDTO) throws BadRequestException{
//        User user = userRepository.findById(userId).orElseThrow(() ->
//                new ResourceNotFoundException(String.format(USER_NOT_FOUND_MSG, userId)));
//
//        Student student = studentRepo.findByUserId(user).orElseThrow(() ->
//                new ResourceNotFoundException(String.format(STUDENT_NOT_FOUND_MSG, userId)));
//
//        Department departments = departmentRepository.findByName(studentDTO.getDepartment())
//                .orElseThrow(() -> new RuntimeException("Error: Department is not found."));
//
//        student.setDepartment(departments);
//
//        studentRepo.save(student);
//    }

    public void deleteById(Long id) throws BadRequestException {
        studentRepo.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(STUDENT_NOT_FOUND_MSG, id)));

        studentRepo.deleteById(id);
    }


}



