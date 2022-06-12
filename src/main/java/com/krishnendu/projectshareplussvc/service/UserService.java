package com.krishnendu.projectshareplussvc.service;

import com.krishnendu.projectshareplussvc.entity.UserEntity;
import com.krishnendu.projectshareplussvc.model.AuthenticationRequest;
import com.krishnendu.projectshareplussvc.model.AuthenticationResponse;
import com.krishnendu.projectshareplussvc.model.User;
import com.krishnendu.projectshareplussvc.repository.interfaces.IUserEntityRepository;
import com.krishnendu.projectshareplussvc.service.interfaces.IUserService;
import com.krishnendu.projectshareplussvc.utils.CookieUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * contract class for User Service
 */
@Service
public class UserService  implements IUserService {

    @Autowired
    private IUserEntityRepository _userRepository;

    @Autowired
    private JwtService _jwtService;

    @Autowired
    private CookieUtil _cookieUtil;

    /**
     * service method to register user
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public ResponseEntity<User> registerUser(User user) throws Exception {
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
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    /**
     * service method to login user
     * @param authenticationRequest
     * @return AuthenticationResponse
     * @throws Exception jwt exception
     */
    @Override
    public ResponseEntity<AuthenticationResponse> loginUser(AuthenticationRequest authenticationRequest) throws Exception {
        AuthenticationResponse tokenCreationResponse = _jwtService.generateJwtToken(authenticationRequest);
        HttpHeaders responseHeaders = new HttpHeaders();
        addAccessTokenCookie(responseHeaders, tokenCreationResponse.getJwtToken());
        return ResponseEntity.status(HttpStatus.OK).headers(responseHeaders).body(tokenCreationResponse);
    }

    private void addAccessTokenCookie(HttpHeaders httpHeaders, String token) {
        httpHeaders.add(
                HttpHeaders.SET_COOKIE,
                _cookieUtil.createAccessTokenCookie(token).toString()
        );
    }
}
