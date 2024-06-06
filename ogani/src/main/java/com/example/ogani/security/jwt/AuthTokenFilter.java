package com.example.ogani.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.ogani.security.service.UserDetailsServiceImpl;



public class AuthTokenFilter extends OncePerRequestFilter  {

    @Autowired
    private JwtUtils jwtUtils;
  
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
  
    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);
  //phuong thuc truu tuong cu no la dofilter
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
      try {
        String jwt = parseJwt(request);
        //validate token xem dung hay chua
        if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
          //lay username tu getusername
          String username = jwtUtils.getUserNameFromJwtToken(jwt);
  //map sang userdetail vi security lam viec voi userdetail thuoc goi java ho tro
          UserDetails userDetails = userDetailsService.loadUserByUsername(username);
          
          UsernamePasswordAuthenticationToken authentication = 
              new UsernamePasswordAuthenticationToken(userDetails,
                                                      null,
                                                      userDetails.getAuthorities());
          
          authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
  
          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      } catch (Exception e) {
        logger.error("Cannot set user authentication: {}", e);
      }
  
      filterChain.doFilter(request, response);
    }
  
    private String parseJwt(HttpServletRequest request) {
      String jwt = jwtUtils.getJwtFromCookies(request);
      return jwt;
    }
    
}