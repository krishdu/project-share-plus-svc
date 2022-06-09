package com.krishnendu.projectshareplussvc.controller;

import com.krishnendu.projectshareplussvc.model.AuthenticationResponse;
import com.krishnendu.projectshareplussvc.service.JwtService;
import com.krishnendu.projectshareplussvc.model.AuthenticationRequest;
import com.krishnendu.projectshareplussvc.service.interfaces.IUserService;
import com.krishnendu.projectshareplussvc.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager _authenticationManager;

    @Autowired
    private IUserService _userService;

    /**
     * endpoint to generate JWT token
     * @param authenticationRequest model
     * @return AuthenticationResponse
     * @throws Exception any type of authentication exception
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> generateAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            _authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUserEmail(), authenticationRequest.getPassword()));
        }catch (DisabledException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new AuthenticationResponse(null, null, "User is Disabled"));
        }catch (BadCredentialsException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthenticationResponse(null, null, "Bad credentials from user"));
        }

        return _userService.loginUser(authenticationRequest);
    }

}
