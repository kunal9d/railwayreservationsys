package com.example.admincontact.controller;

import java.util.*;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.BadCredentialsException;
import com.example.admincontact.model.Admin;
import com.example.admincontact.repositry.AdminContactRepository;
import com.example.admincontact.service.AdminContactService;
import com.example.admincontact.exceptions.UserNotFoundException;
import com.example.admincontact.security.AuthenticationRequest;
import com.example.admincontact.security.AuthenticationResponse;
import com.example.admincontact.security.JwtUtil;
import com.example.admincontact.security.MyUserDetailsService;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("Admin")
public class AdminContactController {
	
	@Autowired
	private AdminContactService adminContactService;
	@Autowired
	private MyUserDetailsService userDetailService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtil jwtTokenUtil;
	Logger logger= org.slf4j.LoggerFactory.getLogger(AdminContactController.class);

	//Create Account
	@PostMapping("createAdmin")
	public ResponseEntity<AuthenticationResponse> addAdmin(@Valid @RequestBody Admin user) {
		Admin admin=new Admin();
		Admin oldAdmin=new Admin();
		oldAdmin=adminContactService.findUserByName(user.getUserName());				
			if (!Objects.isNull(oldAdmin) && oldAdmin.getUserName().equals(user.getUserName()) ) {
				
				return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse
						("Please Select Unique Username!! ") , HttpStatus.OK);
			}
			else {
		admin.setId(user.getId());
		admin.setUserName(user.getUserName());
		admin.setPassword(user.getPassword());
		admin.setPhoneNumber(user.getPhoneNumber());
		try {
			adminContactService.createUser(admin);
		}
		catch(Exception e){
			return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse
					(" Error during singup ") , HttpStatus.OK);
		}
		logger.info("-----------------------Admin Created-------------------");
		return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse
				("Successful singup for client " +user.getUserName()), HttpStatus.OK);
	}}

//display users by their id
	@GetMapping("display/{id}")
	public ResponseEntity<Admin> display(@PathVariable("id") String id){
		Admin ad = adminContactService.getUser(id);
		logger.info("-----------------------Admin display by id-------------------");
		return new ResponseEntity<Admin>(ad , HttpStatus.OK);
	}
	
//list of all users
	@GetMapping("displayAll")
	public List<Admin> displayAll(){
		logger.info("----------------------- All Admin -------------------");
		return adminContactService.getAllUser();
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
		
		UserDetails userdetails= userDetailService.loadUserByUsername(username);
		
		String jwt = jwtTokenUtil.generateToken(userdetails);
		logger.info("-----------------------Admin login successfull-------------------");

		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
	
	@RequestMapping("/getAdmin/{username}")
	public Admin getUser(@PathVariable String username) throws UserNotFoundException {
		logger.info("-----------------------Admin fetched by username --------------------");
		return adminContactService.findUserByName(username);
	}}