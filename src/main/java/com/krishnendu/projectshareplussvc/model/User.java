package com.krishnendu.projectshareplussvc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String userId;
    private String firstName;
    private String lastName;
    private String userEmail;
    private String password;
    /**
     * profile picture
     */
    private String avatar;
    /**
     * profile picture file coming from request pipeline
     */
    private String avatarFile;
    private Date createDate;
    private Date modifyDate;
}
