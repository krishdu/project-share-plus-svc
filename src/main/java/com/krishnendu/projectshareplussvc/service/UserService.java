package com.krishnendu.projectshareplussvc.service;

import com.krishnendu.projectshareplussvc.entity.UserEntity;
import com.krishnendu.projectshareplussvc.model.User;
import com.krishnendu.projectshareplussvc.repository.interfaces.IUserEntityRepository;
import com.krishnendu.projectshareplussvc.service.interfaces.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * contract class for User Service
 */
@Service
public class UserService  implements IUserService {

    @Autowired
    private IUserEntityRepository _userRepository;

    @Override
    public User registerUser(User user) throws Exception {
        try {
            UserEntity userEntity = new UserEntity();
            BeanUtils.copyProperties(user, userEntity);
            userEntity = _userRepository.save(userEntity);
            user.setUserId(userEntity.getUserId());
            user.setCreateDate(userEntity.getCreateDate());
            user.setModifyDate(userEntity.getModifyDate());
        }catch (Exception e) {
            throw new Exception("Couldn't register the user", e);
        }
        return user;
    }
}
