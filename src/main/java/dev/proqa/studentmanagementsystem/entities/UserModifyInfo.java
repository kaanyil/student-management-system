package dev.proqa.studentmanagementsystem.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name ="user_modify_info")
@NoArgsConstructor
@Getter
@Setter
public class UserModifyInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private LocalDateTime lastLoginDateTime;
    private String lastModifiedBy;



}
