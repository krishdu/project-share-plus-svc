package com.krishnendu.projectshareplussvc.service.interfaces;

import com.krishnendu.projectshareplussvc.model.Project;

import java.util.List;

public interface IProjectService {
    Project addPost(Project post) throws Exception;

    List<Project> getPost();
}
