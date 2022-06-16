package com.krishnendu.projectshareplussvc.service;

import com.krishnendu.projectshareplussvc.entity.UserEntity;
import com.krishnendu.projectshareplussvc.model.User;
import com.krishnendu.projectshareplussvc.repository.interfaces.IUserEntityRepository;
import com.krishnendu.projectshareplussvc.service.interfaces.IAuthenticationFacade;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade implements IAuthenticationFacade{
    @Autowired
    private IUserEntityRepository _userRepository;

    /**
     * method get the Authenticated user from JWT token
     * @return User model
     */
    @Override
    public User getUserFromRequestPipeline() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        UserEntity userEntity = _userRepository.findById(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Something went wrong, please login again"));

        User authenticatedUser = new User();
        BeanUtils.copyProperties(userEntity, authenticatedUser);
        return authenticatedUser;
    }

    /**
     * method to get the raw UserDetails (spring Security model)
     * @return UserDetails
     */
    @Override
    public UserEntity getUserEntityFromRequestPipeline() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        UserEntity userEntity = _userRepository.findById(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Something went wrong, please login again"));

        return userEntity;
    }
}
