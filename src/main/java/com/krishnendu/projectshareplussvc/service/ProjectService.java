package com.krishnendu.projectshareplussvc.service;

import com.krishnendu.projectshareplussvc.entity.ProjectEntity;
import com.krishnendu.projectshareplussvc.model.Project;
import com.krishnendu.projectshareplussvc.repository.interfaces.IProjectEntityRepository;
import com.krishnendu.projectshareplussvc.service.interfaces.IProjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService  implements IProjectService {
    private final IProjectEntityRepository _postRepository;

    public ProjectService(IProjectEntityRepository postRepository) {
        _postRepository = postRepository;
    }

    @Override
    public Project addPost(Project post) throws Exception {
        try {
            ProjectEntity postEntity = new ProjectEntity();
            BeanUtils.copyProperties(post, postEntity);

            if(post.getFile() != null && !post.getFile().equalsIgnoreCase("null")) {
                postEntity.setImage(post.getFile());
            }else {
                postEntity.setImage(null);
            }

            postEntity = _postRepository.save(postEntity);
            post.setId(postEntity.getId());
            post.setFile(null);
            post.setImage(postEntity.getImage());

        } catch (Exception e) {
            throw new Exception("Couldn't save the post" +e);
        }

        return post;
    }

    @Override
    public List<Project> getPost() {
        List<ProjectEntity> postEntities =_postRepository.findAll();
        List<Project> posts = new ArrayList<>();
        posts = postEntities.stream()
                .map((postEntity) ->
                        Project.builder()
                                .id(postEntity.getId())
                                .timeStamp(postEntity.getTimeStamp())
                                .email(postEntity.getEmail())
                                .name(postEntity.getName())
                                .post(postEntity.getPost())
                                .image(postEntity.getImage())
                                .profilePic(postEntity.getProfilePic())
                                .build()
                ).collect(Collectors.toList());

        return posts;
    }
}