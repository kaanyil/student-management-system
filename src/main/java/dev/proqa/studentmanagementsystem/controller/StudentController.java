package dev.proqa.studentmanagementsystem.controller;

import dev.proqa.studentmanagementsystem.dto.StudentDTO;
import dev.proqa.studentmanagementsystem.model.Student;
import dev.proqa.studentmanagementsystem.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<StudentDTO>> getAllStudents(){
        final List<StudentDTO> students = studentService.findAll();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/{id}}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STUDENT')")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id){
        final StudentDTO student = studentService.findById(id);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }
    @PostMapping("/admin/auth/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String,Boolean>> addStudent(@RequestBody Student student){
        studentService.addStudent(student);

        Map<String, Boolean> map = new HashMap<>();
        map.put("Student registered successfully!", true);

        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

}
