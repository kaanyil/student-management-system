package dev.proqa.studentmanagementsystem.controller;

import dev.proqa.studentmanagementsystem.dto.AdminDTO;
import dev.proqa.studentmanagementsystem.dto.StudentDTO;
import dev.proqa.studentmanagementsystem.dto.UserDTO;
import dev.proqa.studentmanagementsystem.model.Role;
import dev.proqa.studentmanagementsystem.model.enumeration.UserRole;
import dev.proqa.studentmanagementsystem.repository.RoleRepository;
import dev.proqa.studentmanagementsystem.repository.UserRepository;
import dev.proqa.studentmanagementsystem.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@Produces(MediaType.APPLICATION_JSON)
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;

    @GetMapping("/{id}/auth")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id){
        final StudentDTO student = studentService.findById(id);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PostMapping("/add/auth")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Boolean>> addStudent(@Valid @RequestBody StudentDTO studentDTO) {

        Long userId = studentDTO.getId();
        studentService.addDepartment(userId, studentDTO);

        Map<String, Boolean> map = new HashMap<>();
        map.put("Student added successfully!", true);

        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }



}
