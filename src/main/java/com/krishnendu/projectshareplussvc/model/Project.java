package com.krishnendu.projectshareplussvc.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Project {

    private String id;
    private String post;
    private String name;
    private String email;
    private String image;
    private String file;
    private String profilePic;
    private String timeStamp;
}
