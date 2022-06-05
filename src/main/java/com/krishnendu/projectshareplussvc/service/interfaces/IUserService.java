package com.krishnendu.projectshareplussvc.service.interfaces;

import com.krishnendu.projectshareplussvc.model.User;

public interface IUserService {
    User registerUser(User user) throws Exception;
}
