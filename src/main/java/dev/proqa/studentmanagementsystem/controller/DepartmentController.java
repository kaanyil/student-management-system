package dev.proqa.studentmanagementsystem.controller;

import dev.proqa.studentmanagementsystem.model.Department;
import dev.proqa.studentmanagementsystem.model.enumeration.Departments;
import dev.proqa.studentmanagementsystem.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/department")
@RequiredArgsConstructor
public class DepartmentController {

    private DepartmentService departmentService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STUDENT')")
    public List<Department> findAll(){
        return departmentService.findAllDepartments();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STUDENT')")
    public Department findById(@PathVariable Long id){
        return departmentService.findById(id);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Boolean>> addDepartment(@Valid @RequestBody Department department){

        departmentService.createDepartment(department);

        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }


}
