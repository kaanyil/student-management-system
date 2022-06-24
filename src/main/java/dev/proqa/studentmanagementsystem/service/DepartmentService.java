package dev.proqa.studentmanagementsystem.service;

import dev.proqa.studentmanagementsystem.dto.StudentDTO;
import dev.proqa.studentmanagementsystem.model.Department;
import dev.proqa.studentmanagementsystem.model.enumeration.Departments;
import dev.proqa.studentmanagementsystem.repository.DepartmentRepository;
import dev.proqa.studentmanagementsystem.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final StudentRepository studentRepository;


    public List<StudentDTO> findStudentsByDepartmentName(Departments departmentName) {
        Department department = departmentRepository.findByName(departmentName)
                .orElseThrow(() -> new RuntimeException("Error: Department is not found."));
        return studentRepository.findByDepartment(department);
    }
}
