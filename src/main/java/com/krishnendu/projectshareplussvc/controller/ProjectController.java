package com.krishnendu.projectshareplussvc.controller;

import com.krishnendu.projectshareplussvc.model.Project;
import com.krishnendu.projectshareplussvc.service.interfaces.IProjectService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(value="http://localhost:3000")
@RestController
@RequestMapping("/api/v1/post")
public class ProjectController {
    private final IProjectService _postService;

    public ProjectController(IProjectService postService) {
        _postService = postService;
    }

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Project addPost(Project post) throws Exception {
        post = _postService.addPost(post);
        return post;
    }

    @GetMapping()
    public List<Project> getAllPost() {
        return _postService.getPost();
    }
}
