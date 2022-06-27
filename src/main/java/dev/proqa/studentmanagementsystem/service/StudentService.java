package dev.proqa.studentmanagementsystem.service;

import dev.proqa.studentmanagementsystem.dto.StudentDTO;
import dev.proqa.studentmanagementsystem.exception.ResourceNotFoundException;
import dev.proqa.studentmanagementsystem.model.Department;
import dev.proqa.studentmanagementsystem.model.Student;
import dev.proqa.studentmanagementsystem.model.User;
import dev.proqa.studentmanagementsystem.repository.DepartmentRepository;
import dev.proqa.studentmanagementsystem.repository.StudentRepository;
import dev.proqa.studentmanagementsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepo;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepo;


    private final static String USER_NOT_FOUND_MSG = "user with id %d not found";
    private final static String STUDENT_NOT_FOUND_MSG = "student with id %d not found";


    public List<StudentDTO> findAll() {
        return studentRepo.findAllBy();
    }

    public void addDepartment(Long userId, StudentDTO studentDTO){

        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(USER_NOT_FOUND_MSG, userId)));

        Department department = departmentRepo.findByName(studentDTO.getDepartmentId().getName())
                .orElseThrow(() -> new RuntimeException("Error: Department is not found."));

        Student student = new Student(user, department);

        studentRepo.save(student);

    }

    public StudentDTO findById(Long id) {
        return studentRepo.findByIdOrderById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(STUDENT_NOT_FOUND_MSG, id)));
    }

}



