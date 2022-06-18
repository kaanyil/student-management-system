package dev.proqa.studentmanagementsystem.repository;


import dev.proqa.studentmanagementsystem.exception.ConflictException;
import dev.proqa.studentmanagementsystem.exception.ResourceNotFoundException;
import dev.proqa.studentmanagementsystem.model.Role;
import dev.proqa.studentmanagementsystem.model.User;
import dev.proqa.studentmanagementsystem.model.enumeration.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email) throws ConflictException;

    Optional<User> findByEmail(String email) throws ResourceNotFoundException;

    Optional<User> findByUsername(String username) throws ResourceNotFoundException;

    List<User> findAllBy();

    Optional<User> findByIdOrderByFirstName(Long id) throws ResourceNotFoundException;



}
