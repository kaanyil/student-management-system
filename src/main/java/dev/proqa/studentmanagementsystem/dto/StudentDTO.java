package dev.proqa.studentmanagementsystem.dto;

import dev.proqa.studentmanagementsystem.model.Role;
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
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private String phoneNumber;
    private String gender;
    private Departments department;
    private Role role;


    public StudentDTO(UserDTO userId, String firstName, String lastName,
                      String email, String username, String password,
                      String address, String city, String state, String zipCode,
                      String country, String phoneNumber, String gender, Departments department, Role role) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.department = department;
        this.role = role;
    }


}
