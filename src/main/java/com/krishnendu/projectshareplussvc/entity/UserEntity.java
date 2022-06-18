package com.krishnendu.projectshareplussvc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid", strategy = "uuid2")
    private String userId;

    @Column(unique = true, nullable = false)
    private String userEmail;
    private String firstName;
    private String lastName;
    private String password;

    @Column(nullable = true)
    @Lob
    private String avatar;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<ProjectEntity> projects;

    private Date createDate;
    private Date modifyDate;

    @PrePersist
    protected void onCreate() {
        createDate = new Date();
    }
    @PreUpdate
    protected void onUpdate() {
        modifyDate = new Date();
    }
}
