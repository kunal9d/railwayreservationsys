package com.example.admincontact.controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.admincontact.model.Admin;
import com.example.admincontact.repositry.AdminContactRepository;
import com.example.admincontact.service.AdminContactService;
import com.example.admincontact.security.AuthenticationRequest;
import com.example.admincontact.security.AuthenticationResponse;
import com.example.admincontact.security.JwtUtil;
import com.example.admincontact.security.UserService1;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("Admin")
public class AdminContactController {
	
	@Autowired
	private AdminContactService adminContactService;
	@Autowired
	private UserService1 userServe;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@PostMapping("addAdmin")
	public ResponseEntity<AuthenticationResponse> saveBook(@Valid @RequestBody Admin admin) {
		Admin admin1=new Admin();
		Admin oldAdmin=new Admin();
		oldAdmin=adminContactService.findAdminByName(admin.getUsername());				
			if (!Objects.isNull(oldAdmin) && oldAdmin.getUsername().equals(admin.getUsername()) ) {
				
				return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse
						("Please Select Unique Username!! ") , HttpStatus.OK);
			}
			else {
		admin1.setId(admin.getId());
		admin1.setUsername(admin.getUsername());
		admin1.setPassword(admin.getPassword());
		admin1.setPhoneNumber(admin.getPhoneNumber());
		try {
			adminContactService.addAdmin(admin1);
		}
		catch(Exception e){
			return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse
					(" Error during singup ") , HttpStatus.OK);
		}
		
		return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse
				("Successful singup for client " +admin.getUsername()), HttpStatus.OK);
	}
}
	
	@GetMapping("findAllAdmin")
	public List<Admin> getAdmin(){
		return adminContactService.getAdmin();
		
		
	}
	@GetMapping("findAdminById/{id}")
	public Optional<Admin> getAdminById(@PathVariable String id){
		return adminContactService.getAdminbyId(id);
	}
	
	@PostMapping("authentication")
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
	}
