package dev.proqa.studentmanagementsystem.repository;


import dev.proqa.studentmanagementsystem.dto.StudentDTO;
import dev.proqa.studentmanagementsystem.dto.UserDTO;
import dev.proqa.studentmanagementsystem.exception.BadRequestException;
import dev.proqa.studentmanagementsystem.model.Student;
import dev.proqa.studentmanagementsystem.model.User;
import dev.proqa.studentmanagementsystem.model.enumeration.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;


@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<UserDTO> findByIdOrderById(Long id);

    List<UserDTO> findAllBy();

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);

    @Modifying
    @Query("UPDATE User u " +
            "SET u.firstName = ?2, u.lastName = ?3, u.phoneNumber = ?4, u.email = ?5, " +
            "u.username = ?6, u.address = ?7, u.city = ?8, u.zipCode= ?9, u.state = ?10, u.country = ?11," +
            "u.gender = ?12" +
            "WHERE u.id = ?1")
    void update(Long id, String firstName, String lastName, String phoneNumber, String email,
                String userName, String address, String city, String zipCode,
                String state, String country, String gender)
            throws BadRequestException;

}
