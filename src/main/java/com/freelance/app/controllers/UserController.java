package com.freelance.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freelance.app.dto.UserDto;
import com.freelance.app.dto.UserLoginDto;
import com.freelance.app.security.JwtProvider;
import com.freelance.app.security.JwtResponse;
import com.freelance.app.security.ResponseMessage;
import com.freelance.app.services.IUserService;
import com.freelance.app.util.Routes;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(Routes.USER)
@CrossOrigin(origins = "*")
public class UserController {

	private IUserService userService;
	private AuthenticationManager authenticationManager;
	private JwtProvider jwtProvider;

	@PostMapping("/register")
	public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {
		userService.createUser(userDto);
		return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);

	}

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@RequestBody UserLoginDto userLoginDto) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(), userLoginDto.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtProvider.generateJwtToken(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		return ResponseEntity.ok(new JwtResponse(jwt,"Bearer", userDetails.getUsername(), userDetails.getAuthorities()));
	}
	  @GetMapping("/admin")
	  public String adminAccess() {
	    return ">>> superAdmin Contents";
	  }
}