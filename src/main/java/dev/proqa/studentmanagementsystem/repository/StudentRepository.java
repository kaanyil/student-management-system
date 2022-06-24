package dev.proqa.studentmanagementsystem.repository;


import dev.proqa.studentmanagementsystem.dto.StudentDTO;
import dev.proqa.studentmanagementsystem.exception.ResourceNotFoundException;
import dev.proqa.studentmanagementsystem.model.Department;
import dev.proqa.studentmanagementsystem.model.Student;
import dev.proqa.studentmanagementsystem.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<StudentDTO> findByUserIdOrderByUserId(User userId) throws ResourceNotFoundException;

    Optional<Student> findByUserId(User userId) throws ResourceNotFoundException;

    Optional<StudentDTO> findByIdOrderById(Long id);

    List<StudentDTO> findByDepartment(Department department);

    List<StudentDTO> findAllBy();


}

