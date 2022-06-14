package com.krishnendu.projectshareplussvc.service.interfaces;

import com.krishnendu.projectshareplussvc.model.User;

public interface IAuthenticationFacade {

    /**
     * method get the Authenticated user from JWT token
     * @return User model
     */
    User getUserFromRequestPipeline();
}
