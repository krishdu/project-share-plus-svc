package com.krishnendu.projectshareplussvc.service;

import com.krishnendu.projectshareplussvc.entity.UserEntity;
import com.krishnendu.projectshareplussvc.model.AuthenticationRequest;
import com.krishnendu.projectshareplussvc.model.AuthenticationResponse;
import com.krishnendu.projectshareplussvc.repository.interfaces.IUserEntityRepository;
import com.krishnendu.projectshareplussvc.utils.JwtUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;

/**
 * contract class for Jwt Service
 */
@Service
public class JwtService implements UserDetailsService {

    @Autowired
    private JwtUtils _jwtUtils;
    @Autowired
    private IUserEntityRepository _userRepository;

//    @Autowired
//    private AuthenticationManager _authenticationManager;

    /**
     * method to generate JWT token from user email
     * @param authenticationRequest model
     * @return AuthenticationResponse
     * @throws Exception any-type of jwt exception
     */
    public AuthenticationResponse generateJwtToken(AuthenticationRequest authenticationRequest) throws Exception {
            String userEmail = authenticationRequest.getUserEmail();

            final UserDetails userDetails = loadUserByUsername(userEmail);
            String newJwtToken = _jwtUtils.generateToken(userDetails);

            UserEntity userEntity = _userRepository.findByAttributeUserEmail(userEmail);

            com.krishnendu.projectshareplussvc.model.User  userModel = new com.krishnendu.projectshareplussvc.model.User();
            BeanUtils.copyProperties(userEntity, userModel);

            return new AuthenticationResponse(
                    userModel, newJwtToken, "Token Generated successfully"
            );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = _userRepository.findByAttributeUserEmail(username);
        if(userEntity != null) {
            return new org.springframework.security.core.userdetails.User(
                    userEntity.getUserId(),
                    userEntity.getPassword(),
                    new HashSet<>() //later we can add authorize role for every user
            );
        }else{
            throw new UsernameNotFoundException("User Email is not valid");
        }
    }

    public UserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        UserEntity userEntity = _userRepository.findById(userId)
                                                .orElseThrow(() -> new UsernameNotFoundException("Not a valid user"));

        return new org.springframework.security.core.userdetails.User(
                userEntity.getUserId(),
                userEntity.getPassword(),
                new HashSet<>() //later we can add authorize role for every user
        );

    }




//    private void authenticate(String userName, String userPassword) throws Exception {
//        try {
//            _authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
//        }catch (DisabledException e) {
//            throw new Exception("User is Disabled");
//        }catch (BadCredentialsException e) {
//            throw new Exception("Bad credentials from user");
//        }
//    }
}
