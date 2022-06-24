package dev.proqa.studentmanagementsystem.model;

import dev.proqa.studentmanagementsystem.model.enumeration.UserRole;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private UserRole name;


    @Override
    public String toString() {
        return "Role{" +
                "userRole=" + name +
                '}';
    }


}
