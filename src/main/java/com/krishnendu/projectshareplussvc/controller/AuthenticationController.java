package com.krishnendu.projectshareplussvc.controller;

import com.krishnendu.projectshareplussvc.model.AuthenticationResponse;
import com.krishnendu.projectshareplussvc.service.JwtService;
import com.krishnendu.projectshareplussvc.model.AuthenticationRequest;
import com.krishnendu.projectshareplussvc.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    private JwtService _jwtService;

    @Autowired
    private AuthenticationManager _authenticationManager;

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
            throw new Exception("User is Disabled");
        }catch (BadCredentialsException e) {
            throw new Exception("Bad credentials from user");
        }

       return ResponseEntity.ok(_jwtService.generateJwtToken(authenticationRequest));
    }

}
