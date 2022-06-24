package dev.proqa.studentmanagementsystem.dto;

import dev.proqa.studentmanagementsystem.model.Role;
import dev.proqa.studentmanagementsystem.model.User;
import io.dropwizard.validation.OneOf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {


    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private String phoneNumber;
    private String gender;
    private Role role;


    public UserDTO (User user){
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.address = user.getAddress();
        this.city = user.getCity();
        this.zipCode = user.getZipCode();
        this.state = user.getState();
        this.country = user.getCountry();
        this.phoneNumber = user.getPhoneNumber();
        this.gender = user.getGender();
        this.role = user.getRole();
    }

}
