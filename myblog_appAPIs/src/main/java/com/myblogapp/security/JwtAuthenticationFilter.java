package com.myblogapp.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper ;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//1. get Token
		
		 String requestToken=request.getHeader("Authorization");
		 
		 //Bearer 2352523sdgsg
		 System.out.println(requestToken);
		  String username=null;
		  String token=null;
		  
		  if(requestToken!=null && requestToken.startsWith("Bearer")) {
			  token = requestToken.substring(7);
			  try {
				  username=this.jwtTokenHelper.getUserNameFromToken(token);
			} catch (IllegalArgumentException e ) {
				System.out.println("Unable to get JWT token");
			}
		   catch (ExpiredJwtException e) {
			  System.out.println("JWT token has expired");
		  }
	 catch (MalformedJwtException  e) {
		System.out.println("invalid Jwt");
	}
		  }else {
			  System.out.println("Jwt token does not begin with a Bearer");
		  }
		   
		  //Once we get token than
		  // we validate the token
		  if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			 UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			  if(jwtTokenHelper.validateToken(token, userDetails))
			  {
				  //sahi cahl raha hai 
				  //authentication karna hai 
				  UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
				
				  usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				  
				  SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken );
				  
			  }else
			  {
				  System.out.println("Invalid jwt token");
			  }
			  
		  }else 
		  {
			System.out.println("username is null or context is not null ");
		  } 
		  filterChain.doFilter(request, response);
	}

}
