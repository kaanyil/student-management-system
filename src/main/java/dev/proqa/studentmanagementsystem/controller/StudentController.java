package dev.proqa.studentmanagementsystem.controller;

import dev.proqa.studentmanagementsystem.dto.StudentDTO;
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

    @GetMapping("/{id}/auth}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STUDENT')")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id){
        final StudentDTO student = studentService.findById(id);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STUDENT')")
    public ResponseEntity<StudentDTO> getSecretaryByUserIdAuth(@PathVariable Long userId){
        StudentDTO student = studentService.findByUserId(userId);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<StudentDTO>> getAllStudents(){
        final List<StudentDTO> students = studentService.findAll();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

/*

    @PostMapping("/add")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<Map<String, Boolean>> addStudent(HttpServletRequest request,
                                                             @Valid @RequestBody StudentDTO student) {
        Long userId = (Long) request.getAttribute("id");
        studentService.addStudent(userId, student);

        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @PostMapping("/{userId}/add")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STUDENT')")
    public ResponseEntity<Map<String, Boolean>> addStudentAuth(@PathVariable Long userId,
                                                                 @Valid @RequestBody StudentDTO student) {
        studentService.addStudent(userId, student);

        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @PutMapping("")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<Map<String, Boolean>> updateSecretary(HttpServletRequest request,
                                                                @Valid @RequestBody StudentDTO student) {
        Long userId = (Long) request.getAttribute("id");
        studentService.updateStudent(userId, student);

        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STUDENT')")
    public ResponseEntity<Map<String, Boolean>> updateSecretaryAuth(@PathVariable Long userId,
                                                                    @Valid @RequestBody StudentDTO student) {
        studentService.updateStudent(userId, student);

        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
*/

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Boolean>> deleteSecretary(@PathVariable Long id) {
        studentService.deleteById(id);

        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }


}
