package dev.proqa.studentmanagementsystem.dto;

import dev.proqa.studentmanagementsystem.model.Role;
import dev.proqa.studentmanagementsystem.model.Student;
import dev.proqa.studentmanagementsystem.model.enumeration.Departments;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StudentDTO {

    private Long id;
    private UserDTO userId;
    private Departments department;

    public StudentDTO(Student student) {
        this.id = student.getId();
        this.userId = new UserDTO(student.getUserId());
        this.department = student.getDepartment().getName();
    }


}
