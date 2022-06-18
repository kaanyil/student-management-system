package dev.proqa.studentmanagementsystem;

import dev.proqa.studentmanagementsystem.model.Role;
import dev.proqa.studentmanagementsystem.model.User;
import dev.proqa.studentmanagementsystem.model.enumeration.UserRole;
import dev.proqa.studentmanagementsystem.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
public class StudentManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentManagementSystemApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        source.registerCorsConfiguration("/**", config);
        registrationBean.setFilter(new CorsFilter(source));
        registrationBean.setOrder(0);
        return registrationBean;
    }


    @Bean
    CommandLineRunner run(UserService userservice) {

        return args -> {


//            Role roleStudent = new Role();
//            roleStudent.setUserRole(UserRole.ROLE_STUDENT);
//            Role roleAdmin = new Role();
//            roleAdmin.setUserRole(UserRole.ROLE_ADMIN);
//
//
//            userservice.saveUser(new User("John", "Travolta", "john@gm.com","johnTravolta", "1234K!", "325 S. Embers","Arlington","IL",  "60005", "USA", "7758995555", "Male",roleStudent));
//            userservice.saveUser(new User("Anthony", "Smith", "anthony@gm.com","anthonySmith", "5678Y_", "523 S. Embers","Arlington","IL",  "60005", "USA", "7758995855", "Female",roleAdmin));


        };
    }


}
