package com.krishnendu.projectshareplussvc.service;

import com.krishnendu.projectshareplussvc.entity.ProjectEntity;
import com.krishnendu.projectshareplussvc.entity.UserEntity;
import com.krishnendu.projectshareplussvc.model.Project;
import com.krishnendu.projectshareplussvc.repository.interfaces.IProjectEntityRepository;
import com.krishnendu.projectshareplussvc.service.interfaces.IAuthenticationFacade;
import com.krishnendu.projectshareplussvc.service.interfaces.IProjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * contract class for Project service
 */
@Service
public class ProjectService  implements IProjectService {
    private final IProjectEntityRepository _postRepository;

    public ProjectService(IProjectEntityRepository postRepository) {
        _postRepository = postRepository;
    }

    @Autowired
    private IAuthenticationFacade _authenticationFacade;

    @Override
    public Project addPost(Project post) throws Exception {
        try {
            UserEntity userEntity = _authenticationFacade.getUserEntityFromRequestPipeline();

            ProjectEntity postEntity = new ProjectEntity();
            BeanUtils.copyProperties(post, postEntity);
            postEntity.setUser(userEntity);
            if(post.getFile() != null && !post.getFile().equalsIgnoreCase("null")) {
                postEntity.setImage(post.getFile());
            }else {
                postEntity.setImage(null);
            }

            postEntity = _postRepository.save(postEntity);
            post.setPostId(postEntity.getPostId());
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
                                .postId(postEntity.getPostId())
                                .description(postEntity.getDescription())
                                .image(postEntity.getImage())
                                .owner(postEntity.getUser().getFirstName() +" "+postEntity.getUser().getLastName())
                                .avatar(postEntity.getUser().getAvatar())
                                .createDate(postEntity.getCreateDate())
                                .modifyDate(postEntity.getModifyDate())
                                .build()
                ).collect(Collectors.toList());

        return posts;
    }
}
