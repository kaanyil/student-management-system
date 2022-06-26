package dev.proqa.studentmanagementsystem.service;

import dev.proqa.studentmanagementsystem.model.Department;
import dev.proqa.studentmanagementsystem.repository.DepartmentRepository;
import dev.proqa.studentmanagementsystem.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class DepartmentService {

    private static final String DEPARTMENT_NOT_FOUND_MSG = "Department with id %d not found";
    private final DepartmentRepository departmentRepository;
    private final StudentRepository studentRepository;


    public List<Department> findAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department findById(Long id) {

        return departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format(DEPARTMENT_NOT_FOUND_MSG, id)));
    }

    public void createDepartment(Department department){

        departmentRepository.save(department);

    }

}
