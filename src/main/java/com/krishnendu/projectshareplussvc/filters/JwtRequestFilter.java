package com.krishnendu.projectshareplussvc.filters;

import com.krishnendu.projectshareplussvc.service.JwtService;
import com.krishnendu.projectshareplussvc.utils.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Value("${ACCESS_TOKEN_NAME}")
    private String accessTokenCookieName;
    @Autowired
    private JwtUtils _jwtTokenUtils;
    @Autowired
    private JwtService _jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        Cookie[] cookies = request.getCookies();
//
//        String userId = null;
//        String jwtToken = null;
//
//        if (cookies != null) {
//            jwtToken = Arrays.stream(request.getCookies())
//                    .filter(c -> c.getName().equals(accessTokenCookieName))
//                    .findFirst()
//                    .map(Cookie::getValue)
//                    .orElse(null);
//        }
        final String authorizationHeader = request.getHeader("Authorization");
        String userId = null;
        String jwtToken = null;


        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try{
                jwtToken = authorizationHeader.substring(7);
                userId =_jwtTokenUtils.extractUsername(jwtToken);
            }catch(IllegalArgumentException e){
                System.out.println("Unable to get Jwt token");
            }
            catch (ExpiredJwtException e) {
                System.out.println("Jwt token has expired");
            }
        }else{
            System.out.println("Please login to access this resource");
        }

        if(userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = _jwtService.loadUserByUserId(userId);
            if(_jwtTokenUtils.validateToken(jwtToken, userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                        );
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(request, response);

    }
}
