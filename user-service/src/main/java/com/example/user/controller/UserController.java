package com.example.user.controller;

import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.user.exceptions.UserNotFoundException;
import com.example.user.model.UserModel;
import com.example.user.security.AuthenticationRequest;
import com.example.user.security.AuthenticationResponse;
import com.example.user.security.JwtUtil;
import com.example.user.security.UserService1;
import com.example.user.service.UserService;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("User")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private UserService1 userServe;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	//Create Account
		@PostMapping("createUser")
		public ResponseEntity<AuthenticationResponse> createCustomer(@Valid @RequestBody UserModel user) {
			UserModel userModel=new UserModel();
			UserModel oldusermodel=new UserModel();
			oldusermodel=userService.findUserByName(user.getUserName());				
				if (!Objects.isNull(oldusermodel) && oldusermodel.getUserName().equals(user.getUserName()) ) {
					
					return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse
							("Please Select Unique Username!! ") , HttpStatus.OK);
				}
				else {
			userModel.setId(user.getId());
			userModel.setUserName(user.getUserName());
			userModel.setPassword(user.getPassword());
			userModel.setGender(user.getGender());
			userModel.setPhoneNumber(user.getPhoneNumber());
			try {
				userService.createUser(userModel);
			}
			catch(Exception e){
				return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse
						(" Error during singup ") , HttpStatus.OK);
			}
			
			return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse
					("Successful singup for client " +user.getUserName()), HttpStatus.OK);
		}}

	//display users by their id
		@GetMapping("display/{id}")
		public ResponseEntity<UserModel> display(@PathVariable("id") String id){
			UserModel us = userService.getUser(id);
			return new ResponseEntity<UserModel>(us , HttpStatus.OK);
		}
		
	//list of all users
		@GetMapping("displayAll")
		public List<UserModel> displayAll(){
			return userService.getAllUser();
		}
		
		//for login
		@PostMapping("/auth")
		private ResponseEntity<?> authenticateClient(@RequestBody AuthenticationRequest authreq){
			String username=authreq.getUsername();
			String password= authreq.getPassword();
			try {
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
					
			}
			catch(Exception e) {
				return ResponseEntity.ok(new AuthenticationResponse(" Invalid Credentials..!"));
			}
			
			UserDetails userdetails= userServe.loadUserByUsername(username);
			
			String jwt = jwtTokenUtil.generateToken(userdetails);
			
			return ResponseEntity.ok(new AuthenticationResponse(jwt));
		}
		
		@RequestMapping("/getUser/{username}")
		public UserModel getUser(@PathVariable String username) throws UserNotFoundException {
			return userService.findUserByName(username);
		}

}