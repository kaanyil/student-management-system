package dev.proqa.studentmanagementsystem.controller;

import dev.proqa.studentmanagementsystem.model.User;
import dev.proqa.studentmanagementsystem.security.jwt.JwtUtils;
import dev.proqa.studentmanagementsystem.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.ws.rs.Produces;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
@RestController
@RequestMapping()
@Produces(MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;



    @GetMapping("/user/auth/all")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.fetchAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/user/{id}/auth")
    public ResponseEntity<User> getUserByIdAdmin(@PathVariable Long id){
        User user = userService.findById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


}

