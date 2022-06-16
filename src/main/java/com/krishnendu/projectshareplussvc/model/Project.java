package com.krishnendu.projectshareplussvc.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Project {

    /**
     * project Id
     */
    private String postId;
    /**
     * project description
     */
    private String description;
    /**
     * image stored in  db
     */
    private String image;
    /**
     * file received from request pipeline
     */
    private String file;
    /**
     * user profile picture
     */
    private String avatar;
    /**
     * owner of the project
     */
    private String owner;

    private Date createDate;
    private Date modifyDate;
}
