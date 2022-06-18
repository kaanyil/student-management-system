package dev.proqa.studentmanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name ="user_modify_info")
public class UserModifyInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDateTime;

    @LastModifiedDate
    @Column(name = "updated_date")
    private LocalDateTime updatedDateTime;

    @CreatedDate
    @Column(name = "last_login_date")
    private LocalDateTime lastLoginDateTime;

    @LastModifiedBy
    @Column(name = "last_modified_by")
    private String lastModifiedBy;



}
