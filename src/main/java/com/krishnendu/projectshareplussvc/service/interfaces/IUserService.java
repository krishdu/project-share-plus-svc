package com.krishnendu.projectshareplussvc.service.interfaces;

import com.krishnendu.projectshareplussvc.model.AuthenticationRequest;
import com.krishnendu.projectshareplussvc.model.AuthenticationResponse;
import com.krishnendu.projectshareplussvc.model.User;
import org.springframework.http.ResponseEntity;

public interface IUserService {
    ResponseEntity<User> registerUser(User user) throws Exception;

    ResponseEntity<AuthenticationResponse> loginUser(AuthenticationRequest authenticationRequest) throws Exception;
}
